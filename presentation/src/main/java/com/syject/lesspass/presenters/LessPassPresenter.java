package com.syject.lesspass.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.Template;
import com.syject.domain.interactors.IPasswordInteractor;
import com.syject.domain.interactors.concret.PasswordInteractor;
import com.syject.lesspass.R;
import com.syject.lesspass.views.ILessPassView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

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
    public Observable<String> generatePassword() {

        Template template = new Template.Builder()
                .hasLowerCaseLitters(lessPassView.hasLowerCaseLitters())
                .hasAppearCaseLitters(lessPassView.hasAppearCaseLitters())
                .hasNumbers(lessPassView.hasNumbers())
                .hasSymbols(lessPassView.hasSymbols())
                .length(lessPassView.getLength())
                .counter(lessPassView.getCounter())
                    .build();

        Lesspass lesspass = new Lesspass(
                lessPassView.getSite(),
                lessPassView.getLogin(),
                lessPassView.getMasterPassword()
        );

        return passwordInteractor.getPassword(lesspass, template);
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
}
