package com.intellibins.recyclethis;

import android.app.Activity;
import android.content.Context;
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
    private List<View> mViews = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mViews = buildView();

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return mViews.get(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mViews.get(position);
            }

            @Override
            public int getPosition(Object item) {
                int i = 0;
                for(View view : mViews) {
                    if(view.equals(item))
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

    private List<View> buildView() {
        Card card1 = new Card(this);
        card1.setText(R.string.bin_plastic)
                .setFootnote("1st Address")
                .setImageLayout(Card.ImageLayout.LEFT)
                .addImage(R.drawable.item_paper);
        Card card2 = new Card(this);
        card2.setText(R.string.bin_plastic)
                .setFootnote("2st Address")
                .setImageLayout(Card.ImageLayout.LEFT)
                .addImage(R.drawable.item_paper);
        Card card3 = new Card(this);
        card3.setText(R.string.bin_plastic)
                .setFootnote("3st Address")
                .setImageLayout(Card.ImageLayout.LEFT)
                .addImage(R.drawable.item_paper);
        mViews.add(card1.getView());
        mViews.add(card2.getView());
        mViews.add(card3.getView());
        return mViews;
    }

}
