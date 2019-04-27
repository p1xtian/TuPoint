package t.local.tupoint.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import t.local.tupoint.MainActivity;
import t.local.tupoint.R;
import t.local.tupoint.RestaurantActivity;
import t.local.tupoint.config.WebServices;
import t.local.tupoint.interfaces.InterfaceRetrofit;
import t.local.tupoint.models.Restaurant;
import t.local.tupoint.ui.login.LoginViewModel;
import t.local.tupoint.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private String email;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                email = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                //Anokis

                WebServices webServices = new WebServices();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(webServices.SpringBoot)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InterfaceRetrofit services = retrofit.create(InterfaceRetrofit.class);

                services.
                        Login(email, password).
                        enqueue(new retrofit2.Callback<Restaurant>() {


                            @Override
                            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                                if(response.body() == null)
                                {
                                    Log.d("=TuPoint=>", "Email o Password Error");
                                    Log.d("=TuPoint=>", "Email:"+ email
                                            +" Password:" + password);
                                    Toast.makeText(getApplicationContext(),
                                            "Credentials error" ,
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Restaurant restaurant = response.body();
                                    Log.d("=TuPoint=>", "Email Attempt Login: "+ restaurant.getCorreo());


                                    //Save in SharedPreferences
                                    SharedPreferences credentialsSP =
                                            getSharedPreferences("credentials",
                                                    Context.MODE_PRIVATE);
                                    SharedPreferences.Editor credentialsEditor = credentialsSP.edit();
                                    credentialsEditor.putString("email",restaurant.getCorreo());
                                    Log.d("=iTracking=>",restaurant.getCorreo());
                                    credentialsEditor.putString("razonsocial",restaurant.getRazonsocial());
                                    Log.d("=iTracking=>",restaurant.getRazonsocial());
                                    credentialsEditor.putString("logo",restaurant.getLogo());
                                    Log.d("=iTracking=>",restaurant.getLogo());
                                    credentialsEditor.commit();
                                    Toast.makeText(getApplicationContext(),
                                            "Login Sucefull" ,
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }


                            }

                            @Override
                            public void onFailure(Call<Restaurant> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),
                                        "Server Error" ,

                                        Toast.LENGTH_SHORT).show();
                                Log.d("=TuPoint=>",t.toString());
                            }
                        });

                //loginViewModel.login(usernameEditText.getText().toString(),
                //passwordEditText.getText().toString());
                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
