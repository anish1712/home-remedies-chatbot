package com.code.chatboat.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.chatboat.BaseActivity;
import com.code.chatboat.MyApp;
import com.code.chatboat.adapter.ChatAdapter;
import com.code.chatboat.databinding.ActivityChatBinding;
import com.code.chatboat.model.Message;
import com.code.chatboat.model.ResponseApiItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatActivity extends BaseActivity<ActivityChatBinding> {
    private ChatAdapter chatAdapter;
    private String currentUser = "user1";
    private String otherUser = "user2";
    private String question = "";
    private String type="Levenshtein";
    private String typeLanguage="eng";
    List<Message> messageList = new ArrayList<>();

    @Override
    protected ActivityChatBinding getViewBinding() {
        return ActivityChatBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {

        type = getIntent().getStringExtra("type");
        typeLanguage = getIntent().getStringExtra("typeLanguage");

        if(typeLanguage.equals("guj")) {
            messageList.add(new Message("હાય, હું તમને કેવી રીતે મદદ કરી શકું?", otherUser, currentUser));
        }else {
            messageList.add(new Message("Hi, How can I help you?", otherUser, currentUser));
        }
        chatAdapter = new ChatAdapter(messageList, currentUser);
        binding.recyclerGchat.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerGchat.setAdapter(chatAdapter);

        binding.editGchatMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                question = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonGchatSend.setOnClickListener(view -> {
            if (!question.isEmpty()){
                String ans = "";
                if(type.equalsIgnoreCase("Levenshtein")){
                    //Levenshtein
                    ans = findBestMatchingAnswer(question);
                }else {
                    //N-Gram Matching
                    ans = findBestMatchingAnswerNGram(question);
                }
                Log.e("TAG", "onClick:Answer -->  "+ans );
                messageList.add(new Message(question,currentUser, otherUser));
                messageList.add(new Message(ans,otherUser, currentUser));
                chatAdapter.notifyDataSetChanged();
                question = "";
                binding.editGchatMessage.setText("");
            }
        });
    }

    //use of levenshtein algorithm

    private String findBestMatchingAnswer(String userQuestion) {
        String bestAnswer = "";
        if(typeLanguage.equals("guj")) {
            bestAnswer = "માફ કરશો, પણ મને તમારા પ્રશ્નનો જવાબ મળી શક્યો નથી.";
        }else {
            bestAnswer = "I'm sorry, but I couldn't answer to your question.";
        }
        double bestMatchRatio = 0.0;

        if(typeLanguage.equals("eng")){
            // Loop through each question in the qaList and find the best matching answer
            for (ResponseApiItem qaItem : MyApp.questionAnswerList) {
                try {
                    String question = qaItem.getQuestion().toLowerCase();
                    double ratio = similarityRatio(userQuestion.toLowerCase(), question);

                    // Adjust the matching threshold as needed, e.g., 0.5 for a 50% match
                    if (ratio > 0.3 && ratio > bestMatchRatio) {
                        bestMatchRatio = ratio;
                        bestAnswer = qaItem.getAnswer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            // Loop through each question in the qaList and find the best matching answer
            for (ResponseApiItem qaItem : MyApp.questionAnswerGujList) {
                try {
                    String question = qaItem.getQuestion().toLowerCase();
                    double ratio = similarityRatio(userQuestion.toLowerCase(), question);

                    // Adjust the matching threshold as needed, e.g., 0.5 for a 50% match
                    if (ratio > 0.3 && ratio > bestMatchRatio) {
                        bestMatchRatio = ratio;
                        bestAnswer = qaItem.getAnswer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return bestAnswer;
    }

    // Helper function to calculate similarity ratio (using Levenshtein distance)
    private double similarityRatio(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + cost, Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }

        int maxLen = Math.max(len1, len2);
        if (maxLen == 0) {
            return 1.0;
        }

        return 1.0 - (double) dp[len1][len2] / maxLen;
    }

    //Use of ngram algorithm

    private String findBestMatchingAnswerNGram(String userQuestion) {
        String bestAnswer ="";
        if(typeLanguage.equals("guj")) {
            bestAnswer = "માફ કરશો, પણ મને તમારા પ્રશ્નનો જવાબ મળી શક્યો નથી.";
        }else {
            bestAnswer = "I'm sorry, but I couldn't find answer to your question.";
        }
        double bestSimilarity = 0;

        int n = 3; // Choose the n-gram size (e.g., 3 for trigrams)
        if(typeLanguage.equals("eng")){
            for (ResponseApiItem qaItem : MyApp.questionAnswerList) {
                try {
                    String question = qaItem.getQuestion().toLowerCase();
                    double similarity = calculateNGramSimilarity(userQuestion.toLowerCase(), question, n);
                    if (similarity > bestSimilarity) {
                        bestSimilarity = similarity;
                        bestAnswer = qaItem.getAnswer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            for (ResponseApiItem qaItem : MyApp.questionAnswerGujList) {
                try {
                    String question = qaItem.getQuestion().toLowerCase();
                    double similarity = calculateNGramSimilarity(userQuestion.toLowerCase(), question, n);
                    if (similarity > bestSimilarity) {
                        bestSimilarity = similarity;
                        bestAnswer = qaItem.getAnswer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return bestAnswer;
    }

    private double calculateNGramSimilarity(String str1, String str2, int n) {
        // Tokenize the strings into n-grams
        Set<String> ngramsStr1 = getNGrams(str1, n);
        Set<String> ngramsStr2 = getNGrams(str2, n);

        // Calculate Jaccard similarity or other similarity measures
        double intersectionSize = ngramsStr1.stream().filter(ngramsStr2::contains).count();
        double unionSize = ngramsStr1.size() + ngramsStr2.size() - intersectionSize;
        return intersectionSize / unionSize;
    }

    private Set<String> getNGrams(String str, int n) {
        Set<String> ngrams = new HashSet<>();
        for (int i = 0; i < str.length() - n + 1; i++) {
            ngrams.add(str.substring(i, i + n));
        }
        return ngrams;
    }

}