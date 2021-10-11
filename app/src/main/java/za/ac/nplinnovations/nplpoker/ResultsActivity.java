package za.ac.nplinnovations.nplpoker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import za.ac.nplinnovations.nplpoker.connection.Connection;
import za.ac.nplinnovations.nplpoker.connection.QueryService;
import za.ac.nplinnovations.nplpoker.connection.entities.MainResponse;
import za.ac.nplinnovations.nplpoker.connection.entities.Player;
import za.ac.nplinnovations.nplpoker.pojos.Card;

public class ResultsActivity extends AppCompatActivity {
    Gson gson = new GsonBuilder().setLenient().create();
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(250, TimeUnit.SECONDS)
            .connectTimeout(250, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Connection.getUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build();

    QueryService service = retrofit.create(QueryService.class);

    //RequestQueue queue;
    String url = (Connection.getUrl() + "texas_holdem?");


    private final String TAG = ResultsActivity.class.getSimpleName();

    private String decked_cards;
    private List<PlayersCards> comparisonCards;
    private LinearLayoutCompat viewLoading, viewResults;
    private AppCompatTextView tvWinners;
    private List<Card> available_cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        viewLoading = (LinearLayoutCompat) findViewById(R.id.viewLoading);
        viewResults = (LinearLayoutCompat) findViewById(R.id.viewResults);

        tvWinners = (AppCompatTextView) findViewById(R.id.tvWinners);
        comparisonCards = new ArrayList<>();

        Intent intent = getIntent();
        if (intent.getStringExtra(CardSelectionActivity.DECK_OF_CARDS) != null && (List<Card>) intent.getSerializableExtra(CardSelectionActivity.CARD_LIST) != null){
            //queue = Volley.newRequestQueue(ResultsActivity.this);

            decked_cards = intent.getStringExtra(CardSelectionActivity.DECK_OF_CARDS);
            available_cards = (List<Card>) intent.getSerializableExtra(CardSelectionActivity.CARD_LIST);
            generateComparisonCards(available_cards);
            Call<MainResponse> cardsChecker = service.checkWinners(decked_cards.replaceAll(",", "\\,"), comparisonCards.get(0).toString().replaceAll(",", "\\,"),
                    comparisonCards.get(1).toString().replaceAll(",", "\\,"), comparisonCards.get(2).toString().replaceAll(",", "\\,"), comparisonCards.get(3).toString().replaceAll(",", "\\,"));
            cardsChecker.enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    String result = "";
                    if(response.body() != null && response.body().getWinners().size() > 0) {
                        for (Player player : response.body().getWinners()) {
                            result += player.getResult() + ": " + player.getCards() + "\n";
                            Log.d(TAG, "onResponse: " + player);
                        }
                        Log.d(TAG, "onResponse: winners count (" + response.body().getWinners().size() + ")");
                    }else if(response.body() == null) {
                        result += "Unfortunately we could not generate any winners." ;
                        Log.d(TAG, "onResponse: " + response.errorBody());
                        Log.d(TAG, "onResponse: " + response.raw());
                    }else
                        result += "Error: Could not generate any winners.\n" + response.message();

                    viewLoading.setVisibility(View.GONE);
                    viewResults.setVisibility(View.VISIBLE);
                    tvWinners.setText(result);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    viewLoading.setVisibility(View.GONE);
                    viewResults.setVisibility(View.VISIBLE);
                    tvWinners.setText("Error: " + t.getLocalizedMessage());
                }
            });

/*            url += "cc=" + decked_cards;
            for (PlayersCards pc: comparisonCards){
                url += ",pc[]=" + pc.toString();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    MainResponse mResponse = gson.fromJson(response.toString(), MainResponse.class);
                    String result = "";
                    if(mResponse != null && mResponse.getWinners().size() > 0) {
                        for (Player player : mResponse.getWinners()) {
                            result += player.getResult() + ": " + player.getCards() + "\n";

                        }
                    }else if(mResponse == null) {
                        result += "Unfortunately we could not generate any winners." ;
                        Log.d(TAG, "onResponse: " + response.toString());
                    }else
                        result += "Error: Could not generate any winners.\n" ;

                    viewLoading.setVisibility(View.GONE);
                    viewResults.setVisibility(View.VISIBLE);
                    tvWinners.setText(result);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    viewLoading.setVisibility(View.GONE);
                    viewResults.setVisibility(View.VISIBLE);
                    tvWinners.setText("Error: " + error.getLocalizedMessage());
                    Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                    Log.d(TAG, "url: " + url);
                }
            });

            queue.add(jsonObjectRequest);*/
        }


    }

    private void generateComparisonCards(List<Card> cards) {
        Random r = new Random();

        while (comparisonCards.size() < 4) {
            Card c = cards.get(r.nextInt(cards.size())),
                    c2 = cards.get(r.nextInt(cards.size()));

            if (checkIfExists(c, c2)) {
                comparisonCards.add(new PlayersCards(c.getCode(),c2.getCode()));
            }
        }
    }

    private boolean checkIfExists(Card c, Card c2) {
        String[] splits = decked_cards.split(",");
        for (String code:
             splits) {
            if (c.getCode().equalsIgnoreCase(code) || c2.getCode().equalsIgnoreCase(code)){
                return true;
            }
        }

        for (PlayersCards pc:
                comparisonCards) {
            if (c.getCode().equalsIgnoreCase(pc.card1) || c.getCode().equalsIgnoreCase(pc.card2)
                || c2.getCode().equalsIgnoreCase(pc.card1) || c2.getCode().equalsIgnoreCase(pc.card2)){
                return true;
            }
        }

        return false;
    }

    public void returnToCardSelection(View view) {
        Intent intent = new Intent(view.getContext(), CardSelectionActivity.class);
        intent.putExtra(MainActivity.PASSED_DATA, (Serializable) available_cards);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private class PlayersCards{
        private String card1;
        private String card2;

        public PlayersCards() {
        }

        public PlayersCards(String card1, String card2) {
            this.card1 = card1;
            this.card2 = card2;
        }

        public String getCard1() {
            return card1;
        }

        public String getCard2() {
            return card2;
        }

        @Override
        public String toString() {
            return card1 +"," + card2 ;
        }
    }
}