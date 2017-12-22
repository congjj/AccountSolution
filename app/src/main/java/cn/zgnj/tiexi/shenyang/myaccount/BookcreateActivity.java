package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BookcreateActivity extends AppCompatActivity
{



    private void Load(Intent intent, Bundle savedInstanceState)
    {

    }



    private void CreateBook(View v)
    {
    }

    private void CreateBook_Closed(View v)
    {
        finish() ;
    }












    //region description 初始化

    private Button mButtonExit;
    private Button mButtonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcreate);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);

        mButtonExit .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateBook_Closed(v);
            }
        }) ;

        mButtonCreate .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateBook(v);
            }
        }) ;
    }


    private void LoadView()
    {
        mButtonExit =(Button)findViewById(R.id .btnExit_new) ;
        mButtonCreate  =(Button)findViewById(R.id .btnBookType_new) ;
    }

    //endregion

}
