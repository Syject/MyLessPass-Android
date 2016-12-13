package com.syject.lesspass.ui.screens.lesspass;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.Template;
import com.syject.domain.interactors.IPasswordInteractor;
import com.syject.domain.interactors.concret.PasswordInteractor;
import com.syject.lesspass.R;
import com.syject.lesspass.presenters.IPresenter;
import com.syject.lesspass.ui.LessPassHelper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean
public class LessPassPresenter implements ILessPassPresenter, IPresenter<ILessPassView> {

    private static final String TAG = "LessPassPresenter";
    private static final int DEBOUNCE_TIME = 1000; //1 sec

    private ILessPassView lessPassView;
    private Context context;

    @Bean(PasswordInteractor.class)
    protected IPasswordInteractor passwordInteractor;

    public LessPassPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void login() {
        Toast.makeText(context, "login", Toast.LENGTH_LONG).show();
    }

    @Override
    public void generatePassword() {

        String site = lessPassView.getSite();
        String login = lessPassView.getLogin();
        String masterPassword = lessPassView.getMasterPassword();

        if (site.equals("") || login.equals("") || masterPassword.equals("")) {
            lessPassView.onValidationFailed();
            return;
        }

        lessPassView.onValidationSuccess();

        String length = lessPassView.getLength();
        String counter = lessPassView.getCounter();

        if (!LessPassHelper.validateNumbers(length)) {
            lessPassView.onValidationLengthError();
            return;
        }

        if (!LessPassHelper.validateNumbers(counter)) {
            lessPassView.onValidationCounterError();
            return;
        }

        int len = LessPassHelper.getInt(length);
        len = len != 0 ? len : Template.len;

        Template template = new Template.Builder()
                .hasLowerCaseLitters(lessPassView.hasLowerCaseLitters())
                .hasAppearCaseLitters(lessPassView.hasAppearCaseLitters())
                .hasNumbers(lessPassView.hasNumbers())
                .hasSymbols(lessPassView.hasSymbols())
                .length(len)
                .counter(LessPassHelper.getInt(counter))
                    .build();

        Lesspass lesspass = new Lesspass(site,login,masterPassword);

        passwordInteractor.getPassword(lesspass, template)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> {
                    lessPassView.onPasswordGenerated(p);
                    hidePasswordAfter(30000);
                });
        //lessPassView.setPassword(passwordInteractor.getPassword(lesspass, template));
    }

    @Override
    public void copyToClipboard() {
        String password = lessPassView.getPassword();
        if (password != null && !password.equals("")) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText(context.getString(R.string.password), password);
            clipboardManager.setPrimaryClip(data);
            Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show();
            hidePasswordAfter(10000);
        }
    }

    @Override
    public void setView(@NonNull ILessPassView view) {
        this.lessPassView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.lessPassView = null;
    }

    public void hidePasswordAfter(int delay) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            lessPassView.resetPasswordGenerated();
        }, delay);
    }

}
