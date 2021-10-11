package za.ac.nplinnovations.nplpoker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import za.ac.nplinnovations.nplpoker.pojos.Card;

public class CardSelectionActivity extends AppCompatActivity {
    private final String TAG = CardSelectionActivity.class.getSimpleName();
    public static final String DECK_OF_CARDS = "cardDeck";
    public static final String CARD_LIST = "cardList";

    private List<Card> mCards;
    private MaterialAutoCompleteTextView atcCardOne, atcCardTwo, atcCardThree, atcCardFour, atcCardFive;
    private AppCompatImageView ivCardOne, ivCardTwo, ivCardThree, ivCardFour, ivCardFive;
    private String c1, c2, c3, c4, c5;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selection);
        atcCardOne = (MaterialAutoCompleteTextView) findViewById(R.id.atcCardOne);
        atcCardTwo = (MaterialAutoCompleteTextView) findViewById(R.id.atcCardTwo);
        atcCardThree = (MaterialAutoCompleteTextView) findViewById(R.id.atcCardThree);
        atcCardFour = (MaterialAutoCompleteTextView) findViewById(R.id.atcCardFour);
        atcCardFive = (MaterialAutoCompleteTextView) findViewById(R.id.atcCardFive);

        ivCardOne = (AppCompatImageView) findViewById(R.id.ivCardOne);
        ivCardTwo = (AppCompatImageView) findViewById(R.id.ivCardTwo);
        ivCardThree = (AppCompatImageView) findViewById(R.id.ivCardThree);
        ivCardFour = (AppCompatImageView) findViewById(R.id.ivCardFour);
        ivCardFive = (AppCompatImageView) findViewById(R.id.ivCardFive);

        //Initialise toast
        mToast = Toast.makeText(this, "Card already revealed.", Toast.LENGTH_SHORT);

        Intent previousIntent = getIntent();
        if (previousIntent != null){
            mCards = (List<Card>) previousIntent.getSerializableExtra(MainActivity.PASSED_DATA);
            List<String> clues = new ArrayList<>();
            Log.d(TAG, "onCreate: " + mCards.size() + " cards received.");
            for (Card card : mCards){
                clues.add(card.getCode());
                clues.add(card.getName());
            }

            setupAutoCompleteListeners(clues);
            setupCardOnClickListeners();
        }
    }

    private void setupCardOnClickListeners() {
        Random r = new Random();
        ivCardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.cancel();
                if (c1 == null){
                    Card card = mCards.get(r.nextInt(mCards.size()));
                    ivCardOne.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    atcCardOne.setText(card.getName());
                    atcCardOne.setEnabled(false);
                    c1 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    mToast.show();
                }
            }
        });

        ivCardTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.cancel();
                if (c2 == null){
                    Card card = mCards.get(r.nextInt(mCards.size()));
                    ivCardTwo.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    atcCardTwo.setText(card.getName());
                    atcCardTwo.setEnabled(false);
                    c2 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    mToast.show();
                }
            }
        });

        ivCardThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.cancel();
                if (c3 == null){
                    Card card = mCards.get(r.nextInt(mCards.size()));
                    ivCardThree.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    atcCardThree.setText(card.getName());
                    atcCardThree.setEnabled(false);
                    c3 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    mToast.show();
                }
            }
        });

        ivCardFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.cancel();
                if (c4 == null){
                    Card card = mCards.get(r.nextInt(mCards.size()));
                    ivCardFour.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    atcCardFour.setText(card.getName());
                    atcCardFour.setEnabled(false);
                    c4 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    mToast.show();
                }
            }
        });

        ivCardFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToast.cancel();
                if (c5 == null){
                    Card card = mCards.get(r.nextInt(mCards.size()));
                    ivCardFive.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    atcCardFive.setText(card.getName());
                    atcCardFive.setEnabled(false);
                    c5 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    mToast.show();
                }
            }
        });
    }

    private void setupAutoCompleteListeners(List<String> clues) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, clues);

        atcCardOne.setAdapter(adapter);
        atcCardTwo.setAdapter(adapter);
        atcCardThree.setAdapter(adapter);
        atcCardFour.setAdapter(adapter);
        atcCardFive.setAdapter(adapter);

        atcCardOne.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d(TAG, "selected value: " + atcCardOne.getText().toString());

                Card card = findCard(atcCardOne.getText().toString());
                if (card != null){
                    atcCardOne.setEnabled(false);
                    ivCardOne.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    c1 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    atcCardOne.setError(getResources().getString(R.string.invalid_card));
                    atcCardOne.requestFocus();
                }
            }
        });

        atcCardTwo.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d(TAG, "selected value: " + atcCardTwo.getText().toString());

                Card card = findCard(atcCardTwo.getText().toString());
                if (card != null){
                    atcCardTwo.setEnabled(false);
                    ivCardTwo.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    c2 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    atcCardTwo.setError(getResources().getString(R.string.invalid_card));
                    atcCardTwo.requestFocus();
                }
            }
        });

        atcCardThree.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d(TAG, "selected value: " + atcCardThree.getText().toString());

                Card card = findCard(atcCardThree.getText().toString());
                if (card != null){
                    atcCardThree.setEnabled(false);
                    ivCardThree.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    c3 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    atcCardThree.setError(getResources().getString(R.string.invalid_card));
                    atcCardThree.requestFocus();
                }
            }
        });

        atcCardFour.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d(TAG, "selected value: " + atcCardFour.getText().toString());

                Card card = findCard(atcCardFour.getText().toString());
                if (card != null){
                    atcCardFour.setEnabled(false);
                    ivCardFour.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    c4 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    atcCardFour.setError(getResources().getString(R.string.invalid_card));
                    atcCardFour.requestFocus();
                }
            }
        });

        atcCardFive.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.d(TAG, "selected value: " + atcCardFive.getText().toString());

                Card card = findCard(atcCardFive.getText().toString());
                if (card != null){
                    atcCardFive.setEnabled(false);
                    ivCardFive.setImageDrawable(getResources().getDrawable(card.getImage(), null));
                    c5 = card.getCode();
                    checkDeckIfComplete();
                }else{
                    atcCardFive.setError(getResources().getString(R.string.invalid_card));
                    atcCardFive.requestFocus();
                }
            }
        });
    }

    private void checkDeckIfComplete() {
        if (c1 != null && c2 != null && c3 != null &&
            c4 != null && c5 != null)
            ((MaterialButton)findViewById(R.id.submit)).setVisibility(View.VISIBLE);
    }

    private Card findCard(String reference) {
        for(Card card: mCards){
            if (card.getCode().equalsIgnoreCase(reference)||
                card.getName().equalsIgnoreCase(reference)){
                return card;
            }
        }

        return null;
    }



    public void checkcards(View view) {
        Toast.makeText(view.getContext(), "Cards to submit\n" + c1+"," + c2 + "," + c3 + "," + c4 + "," + c5
                , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(), ResultsActivity.class);
        intent.putExtra(DECK_OF_CARDS, c1 + "," + c2 + "," + c3 + "," + c4 + "," + c5);
        intent.putExtra(CARD_LIST, (Serializable) mCards);
        startActivity(intent);
    }
}