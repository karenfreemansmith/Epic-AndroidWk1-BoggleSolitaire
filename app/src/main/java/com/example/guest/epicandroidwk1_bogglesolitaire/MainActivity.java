package com.example.guest.epicandroidwk1_bogglesolitaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.RandomStringUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind (R.id.randomStringView) TextView mRandomStringView;
    @Bind (R.id.addWordInput) EditText mAddWordInput;
    @Bind (R.id.addWordButton) Button mAddWordButton;
    @Bind (R.id.dunNaoButton) Button mDunNaoButton;
    @Bind (R.id.wordListView) TextView mWordListView;

    ArrayList<String> mWordList = new ArrayList<String>();
    private String mScrambled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        String randomString = RandomStringUtils.random(6, 'A', 'Z', true, false);
//        String vowels = "AEIOU";
//        String vowelString = RandomStringUtils.random(2, vowels.toCharArray());
        String[] words = getResources().getStringArray(R.array.words_array);
        Random random = new Random();
        String word = words[random.nextInt(words.length)];
        List<String> chars = Arrays.asList(word.split(""));
        Collections.shuffle(chars);
        mScrambled = TextUtils.join("", chars);
        mRandomStringView.setText(mScrambled);

        mAddWordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    String wordToAdd = mAddWordInput.getText().toString();
                    mAddWordInput.setText("");
                    String message = handleWord(wordToAdd);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    handled = true;
                }
                return handled;
            }
        });

        mAddWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordToAdd = mAddWordInput.getText().toString();
                mAddWordInput.setText("");
                String message = handleWord(wordToAdd);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                mWordListView.setText(TextUtils.join(", ", mWordList));
            }
        });

        mDunNaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("wordList", mWordList);
                startActivity(intent);
            }
        });
    }

    public String handleWord(String word){
        if(word.length() < 3){
            return "Your word is too short!";
        } else if(mWordList.contains(word)){
            return "You've already guessed that word!";
        }
        else {
            String tempScrambled = mScrambled;
            for(int i = 0; i < word.length(); i++){
                String currentChar = Character.toString(word.charAt(i));
                if(!tempScrambled.contains(currentChar)){
                    return "One of your letters is not in the word, cheater";
                } else {
                    tempScrambled = tempScrambled.substring(0, tempScrambled.indexOf(currentChar)) + tempScrambled.substring(tempScrambled.indexOf(currentChar)+1);
                }
            }
            mWordList.add(word);
            return "word added to list!";
        }
    }


}
