package com.ceylonux.www.getstarted;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroductionManger introductionManger;
    private int []layouts;
    private  TextView[] dots;
    private LinearLayout dotLayout;
    Button _btnNext,_btnSkip;
    private ViewPageAdapter ViewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        introductionManger = new IntroductionManger(this);
        if(!introductionManger.Check()){
            introductionManger.setFirst(false);
            Intent i = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(i);
            finish();
        }
        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);

        viewPager =(ViewPager)findViewById(R.id.viewPager);
        dotLayout=(LinearLayout)findViewById(R.id.layoutDots);
        _btnNext=(Button)findViewById(R.id.btnNext);
        _btnSkip=(Button)findViewById(R.id.btnSkip);

        layouts= new int[]{R.layout.activity_screen1,R.layout.activity_screen2,R.layout.activity_screen3,R.layout.activity_screen4,R.layout.activity_screen5};
        addBottomDots(0);
        changeStatusBarColor();
        ViewPageAdapter= new ViewPageAdapter();
        viewPager.setAdapter(ViewPageAdapter);
        viewPager.addOnPageChangeListener(viewListner);

        _btnSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //introductionManger.setFirst(false);
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
                finish();
            }
        });

        _btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    //introductionManger.setFirst(false);
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private  void addBottomDots(int position){
        dots = new TextView[layouts.length];
        int[] colorActive =getResources().getIntArray(R.array.dot_active);
        int[] colorInActive =getResources().getIntArray(R.array.dot_inactive);
        dotLayout.removeAllViews();
        for(int i=0;i< dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive[position]);
            dotLayout.addView(dots[i]);
        }

        if(dots.length>0){
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem()+1;
    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if(position==layouts.length-1){
                    _btnNext.setText("Proceed");
                    _btnSkip.setVisibility(View.GONE);
                }
                else{
                    _btnNext.setText("Next");
                    _btnSkip.setVisibility(View.GONE);
                    _btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPageAdapter extends PagerAdapter{
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater= (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v =layoutInflater.inflate(layouts[position],container,(false));
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return  layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}