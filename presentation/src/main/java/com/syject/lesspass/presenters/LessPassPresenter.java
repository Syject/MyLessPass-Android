package com.syject.lesspass.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.syject.domain.Template;
import com.syject.domain.utils.PasswordUtils;
import com.syject.lesspass.LessPassHelper;
import com.syject.lesspass.R;
import com.syject.lesspass.views.ILessPassView;

import org.androidannotations.annotations.EBean;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

@EBean
public class LessPassPresenter implements ILessPassPresenter, IPresenter<ILessPassView> {

    private static final String TAG = "LessPassPresenter";
    private static final int DEBOUNCE_TIME = 1000; //1 sec

    private ILessPassView lessPassView;
    private Context context;
    private Subscription subscription;

    public LessPassPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void login() {
        Toast.makeText(context, "login", Toast.LENGTH_LONG).show();
    }

    private String generatePassword(String site, String login, String masterPassword, Integer length, Integer counter) {

        //length = LessPassHelper.convertInt(lessPassView.getLengthView().getText().toString());
        //counter = LessPassHelper.convertInt(lessPassView.getCounterView().getText().toString());

        Template template = new Template(
                lessPassView.hasLowerCaseLittersView().isChecked(),
                lessPassView.hasAppearCaseLittersView().isChecked(),
                lessPassView.hasNumbersView().isChecked(),
                lessPassView.hasSymbolsView().isChecked(),
                length,
                counter
        );
        return PasswordUtils.generatePassword(
                site,
                login,
                masterPassword,
                template
        );
    }

    @Override
    public void copyToClipboard() {
        String password = lessPassView.getPasswordView().toString();
        if (password != null && !password.equals("")) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText(context.getString(R.string.password), password);
            clipboardManager.setPrimaryClip(data);
            Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initView() {
        subscription = Observable.combineLatest(
                RxTextView.textChanges(lessPassView.getSiteView())
                        .filter(s -> s.length() > 0),

                RxTextView.textChanges(lessPassView.getLoginView())
                        .filter(l -> l.length() > 0),

                RxTextView.textChanges(lessPassView.getMasterPasswordView())
                        .filter(mp -> mp.length() > 0),

                RxTextView.textChanges(lessPassView.getLengthView())
                        .filter(len -> {
                            boolean b = LessPassHelper.validateLength(len);
                            if (!b) {
                                lessPassView.onValidationLengthError();
                            }
                            return b;
                        })
                        .map(LessPassHelper::convertInt),

                RxTextView.textChanges(lessPassView.getCounterView())
                        .filter(c -> {
                            boolean b = LessPassHelper.validateLength(c);
                            if (!b) {
                                lessPassView.onValidationCounterError();
                            }
                            return b;
                        })
                        .map(LessPassHelper::convertInt),

                (s, l, mp, len, c) -> generatePassword(s.toString(), l.toString(), mp.toString(), len, c)
        )
                .filter(pass -> pass != null)
                .subscribe(pass -> lessPassView.getPasswordView().setText(pass));
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
        subscription.unsubscribe();
    }
}
