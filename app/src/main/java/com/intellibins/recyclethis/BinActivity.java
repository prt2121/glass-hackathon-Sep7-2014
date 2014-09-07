package com.intellibins.recyclethis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

public class BinActivity extends Activity {

    private CardScrollView mCardScroller;
    private List<Card> mCards = new ArrayList<Card>();
    private int mTextId = Integer.MAX_VALUE;
    private int mDrawableId = Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();
        String type = (intent != null) ? intent.getStringExtra(CaptureActivity.BIN_TYPE) : "paper bin";
        mTextId = type.equalsIgnoreCase("paper bin") ? R.string.bin_paper : R.string.bin_plastic;
        mDrawableId = type.equalsIgnoreCase("paper bin") ? R.drawable.bin_plastic : R.drawable.bin_plastic;

        mCards = buildView();

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return mCards.get(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mCards.get(position).getView();
            }

            @Override
            public int getPosition(Object item) {
                int i = 0;
                for(Card card : mCards) {
                    if(card.getView().equals(item))
                        return i;
                    i++;
                }
                return AdapterView.INVALID_POSITION;
            }
        });
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.playSoundEffect(Sounds.DISALLOWED);
            }
        });
        setContentView(mCardScroller);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    private List<Card> buildView() {
        List<Location> locations = MyApp.getBinLocations();
        Card card1 = new Card(this);
        card1.setText(mTextId)
                .setFootnote(locations.get(0).getProvider())
                .setImageLayout(Card.ImageLayout.LEFT)
                .addImage(mDrawableId);
        Card card2 = new Card(this);
        card2.setText(mTextId)
                .setFootnote(locations.get(1).getProvider())
                .setImageLayout(Card.ImageLayout.LEFT)
                .addImage(mDrawableId);
        Card card3 = new Card(this);
        card3.setText(mTextId)
                .setFootnote(locations.get(2).getProvider())
                .setImageLayout(Card.ImageLayout.LEFT)
                .addImage(mDrawableId);
        mCards.add(card1);
        mCards.add(card2);
        mCards.add(card3);
        return mCards;
    }

}
