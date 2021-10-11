package za.ac.nplinnovations.nplpoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import za.ac.nplinnovations.nplpoker.pojos.Card;

public class MainActivity extends AppCompatActivity {
    public static final String PASSED_DATA = "passedData";
    LoadCards runningTask;
    CircularProgressIndicator mProgressIndicator;
    MaterialButton mButton;
    WeakReference<Context> mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressIndicator = (CircularProgressIndicator) findViewById(R.id.progress_bar);
        mButton = (MaterialButton) findViewById(R.id.btnBegin);
        mContext = new WeakReference<Context>(getBaseContext());
    }

    public void redirectToCompanyWebsite(View view) {
        Uri uri = Uri.parse("https://www.rsaweb.co.za/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void startPlaying(View view) {
        if(runningTask == null)
            initialiseCards();
    }

    private void initialiseCards() {
        runningTask = new LoadCards();
        runningTask.execute();
    }

    private final class LoadCards extends AsyncTask<Void, Card, ArrayList<Card>> {

        @Override
        protected ArrayList<Card> doInBackground(Void... voids) {
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(new Card("AS", "Ace of spades", R.drawable.ace_of_spades));
            cards.add(new Card("AC", "Ace of clubs", R.drawable.ace_of_clubs));
            cards.add(new Card("AH", "Ace of hearts", R.drawable.ace_of_hearts));
            cards.add(new Card("AD", "Ace of diamonds", R.drawable.ace_of_diamonds));

            cards.add(new Card("J", "Joker", R.drawable.joker));

            cards.add(new Card("JC", "Jack of clubs", R.drawable.jack_of_clubs));
            cards.add(new Card("JH", "Jack of hearts", R.drawable.jack_of_hearts));
            cards.add(new Card("JS", "Jack of spades", R.drawable.jack_of_spades));
            cards.add(new Card("JD", "Jack of diamonds", R.drawable.jack_of_diamonds));

            cards.add(new Card("QC", "Queen of clubs", R.drawable.queen_of_clubs));
            cards.add(new Card("QH", "Queen of hearts", R.drawable.queen_of_hearts));
            cards.add(new Card("QS", "Queen of spades", R.drawable.queen_of_spades));
            cards.add(new Card("QD", "Queen of diamonds", R.drawable.queen_of_diamonds));


            cards.add(new Card("KC", "King of clubs", R.drawable.king_of_clubs));
            cards.add(new Card("KH", "King of hearts", R.drawable.king_of_hearts));
            cards.add(new Card("KS", "King of spades", R.drawable.king_of_spades));
            cards.add(new Card("KD", "King of diamonds", R.drawable.king_of_diamonds));

            cards.add(new Card("10C", "10 of clubs", R.drawable.ten_of_clubs));
            cards.add(new Card("10D", "10 of diamonds", R.drawable.ten_of_diamonds));
            cards.add(new Card("10H", "10 of hearts", R.drawable.ten_of_hearts));
            cards.add(new Card("10S", "10 of spades", R.drawable.ten_of_spades));

            cards.add(new Card("9C", "9 of clubs", R.drawable.nine_of_clubs));
            cards.add(new Card("9D", "9 of diamonds", R.drawable.nine_of_diamonds));
            cards.add(new Card("9H", "9 of hearts", R.drawable.nine_of_hearts));
            cards.add(new Card("9S", "9 of spades", R.drawable.nine_of_spades));

            cards.add(new Card("8C", "8 of clubs", R.drawable.eight_of_clubs));
            cards.add(new Card("8D", "8 of diamonds", R.drawable.eight_of_diamonds));
            cards.add(new Card("8H", "8 of hearts", R.drawable.eight_of_hearts));
            cards.add(new Card("8S", "8 of spades", R.drawable.nine_of_spades));

            cards.add(new Card("7C", "7 of clubs", R.drawable.seven_of_clubs));
            cards.add(new Card("7D", "7 of diamonds", R.drawable.seven_of_diamonds));
            cards.add(new Card("7H", "7 of hearts", R.drawable.seven_of_hearts));
            cards.add(new Card("7S", "7 of spades", R.drawable.seven_of_spades));

            cards.add(new Card("6C", "6 of clubs", R.drawable.six_of_clubs));
            cards.add(new Card("6D", "6 of diamonds", R.drawable.six_of_diamonds));
            cards.add(new Card("6H", "6 of hearts", R.drawable.six_of_hearts));
            cards.add(new Card("6S", "6 of spades", R.drawable.six_of_spades));

            cards.add(new Card("5C", "5 of clubs", R.drawable.five_of_clubs));
            cards.add(new Card("5D", "5 of diamonds", R.drawable.five_of_diamonds));
            cards.add(new Card("5H", "5 of hearts", R.drawable.five_of_hearts));
            cards.add(new Card("5S", "5 of spades", R.drawable.five_of_spades));

            cards.add(new Card("4C", "4 of clubs", R.drawable.four_of_clubs));
            cards.add(new Card("4D", "4 of diamonds", R.drawable.four_of_diamonds));
            cards.add(new Card("4S", "4 of spades", R.drawable.four_of_spades));
            cards.add(new Card("4H", "4 of hearts", R.drawable.four_of_hearts));

            cards.add(new Card("3C", "3 of clubs", R.drawable.three_of_clubs));
            cards.add(new Card("3D", "3 of diamonds", R.drawable.three_of_diamonds));
            cards.add(new Card("3H", "3 of hearts", R.drawable.three_of_hearts));
            cards.add(new Card("3S", "3 of spades", R.drawable.three_of_spades));

            cards.add(new Card("2C", "2 of clubs", R.drawable.two_of_clubs));
            cards.add(new Card("2D", "2 of diamonds", R.drawable.two_of_diamonds));
            cards.add(new Card("2H", "2 of hearts", R.drawable.two_of_hearts));
            cards.add(new Card("2S", "2 of spades", R.drawable.two_of_spades));

            return cards;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mButton.setVisibility(View.GONE);
            mProgressIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            super.onPostExecute(cards);
            Intent intent = new Intent(mContext.get(), CardSelectionActivity.class);
            intent.putExtra(PASSED_DATA, (Serializable) cards);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.get().startActivity(intent);
            mProgressIndicator.setVisibility(View.GONE);
            mButton.setVisibility(View.VISIBLE);
        }

    }
}