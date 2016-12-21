package com.syject.lesspass.ui.screens.lesspass;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.syject.data.entities.Lesspass;
import com.syject.data.entities.Options;
import com.syject.data.entities.Template;
import com.syject.data.preferences.PreferencesManager;
import com.syject.domain.interactors.IPasswordInteractor;
import com.syject.domain.interactors.concret.PasswordInteractor;
import com.syject.domain.utils.LessPassHelper;
import com.syject.domain.utils.SystemUtils;
import com.syject.lesspass.R;
import com.syject.lesspass.presenters.IPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EBean
public class LessPassPresenter implements ILessPassPresenter, IPresenter<ILessPassView> {

    private static final int HIDE_PASSWORD_AFTER_GENERATE = 30000; //30 sec
    private static final int HIDE_PASSWORD_AFTER_COPY = 10000; //10 sec
    private final Handler handler = new Handler();

    private ILessPassView lessPassView;

    private Runnable runnable;

    private Context context;

    @Bean(PasswordInteractor.class)
    protected IPasswordInteractor passwordInteractor;

    @Bean
    protected PreferencesManager preferences;

    public LessPassPresenter(Context context) {
        this.context = context;
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

        Lesspass lesspass = new Lesspass(site, login, masterPassword);

        lessPassView.onPasswordGenerating();
        passwordInteractor.getPassword(lesspass, template)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> {
                    lessPassView.onPasswordGenerated(p);
                    hidePasswordAfter(HIDE_PASSWORD_AFTER_GENERATE);
                });
    }

    @Override
    public void copyToClipboard() {
        String password = lessPassView.getPassword();
        if (password != null && !password.equals("")) {
            SystemUtils.copyToClipboard(context, password, context.getString(R.string.password));
            Toast.makeText(context, R.string.copied, Toast.LENGTH_SHORT).show();
            hidePasswordAfter(HIDE_PASSWORD_AFTER_COPY);
        }
    }

    @Override
    public void checkOptionsSaved() {
        Options options = getFreshOptions();
        Options savedOptions = preferences.getOptions();

        if(options.equals(savedOptions)) {
            lessPassView.onOptionsSaved();
        } else {
            lessPassView.onOptionsNotSaved();
        }
    }

    @Override
    public void saveOptionsAsDefault() {
        preferences.setOptions(getFreshOptions());
        lessPassView.onOptionsSaved();
    }

    @Override
    public void initFields() {
        Options savedOptions = preferences.getOptions();
        if (savedOptions != null) {
            lessPassView.setOptions(savedOptions);
        }
    }

    private Options getFreshOptions() {
        return new Options.Builder()
                .site(lessPassView.getSite())
                .login(lessPassView.getLogin())
                .hasLowerCaseLitters(lessPassView.hasLowerCaseLitters())
                .hasAppearCaseLitters(lessPassView.hasAppearCaseLitters())
                .hasNumbers(lessPassView.hasNumbers())
                .hasSymbols(lessPassView.hasSymbols())
                .length(LessPassHelper.getInt(lessPassView.getLength()))
                .counter(LessPassHelper.getInt(lessPassView.getCounter()))
                    .build();
    }

    @Override
    public void setView(@NonNull ILessPassView view) {
        this.lessPassView = view;
        runnable = () -> lessPassView.resetPasswordGenerated(true);
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
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }

}
