package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import nanodegree.udacity.jokedisplaylibrary.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    String retrievedJoke;
    private static MyApi myApiService = null;
    ProgressBar spinner;
    Button jokeButton;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        spinner = root.findViewById(R.id.progressBar1);
        jokeButton = root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });
        return root;
    }

    public void tellJoke() {
        new EndpointsAsyncTask().execute(getActivity());
        if (retrievedJoke != null) {
            Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
            intent.putExtra(JokeDisplayActivity.JOKE_KEY, retrievedJoke);
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_SHORT);
        }
    }

    class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {

        private Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EspressoIdlingResouce.increment();
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Context... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            try {
                return myApiService.pickJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            EspressoIdlingResouce.decrement();
            spinner.setVisibility(View.GONE);
            retrievedJoke = result;
        }
    }
}
