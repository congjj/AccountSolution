package cn.zgnj.tiexi.shenyang.myaccount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

public class SubjectActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        this.InitializeComponent(savedInstanceState);
    }

    //region description 初始化
    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        /**
         * 载入
         */
        Load(savedInstanceState);
        /**
         * 按钮生成
         */
        this.mBtnSubjectCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createOneSubject(view);
            }
        });
        /**
         * rdbIn
         */
        this.mRdbIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                RbdInChecked(compoundButton, b);
            }
        });
        /**
         * rdbOut
         */
        this.mRdbOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                RbdOutChecked(compoundButton, b);
            }
        });
    }


    private long accountBookID;
    private TextView mTxvSubjectTitle;
    private EditText mEdtSubjectRemark;
    private EditText mEdtSubjectName;
    private Button mBtnSubjectCreate;
    private RadioButton mRdbIn;
    private RadioButton mRdbOut;
    private RecyclerView mRecyclerView;
    private void LoadView()
    {
        this.mTxvSubjectTitle = (TextView) findViewById(R.id.txvSubjectTitle);
        this.mRdbIn = (RadioButton) findViewById(R.id.rdbIn);
        this.mRdbOut = (RadioButton) findViewById(R.id.rdbOut);
        this.mEdtSubjectName = (EditText) findViewById(R.id.edtSubjectName);
        this.mEdtSubjectRemark = (EditText) findViewById(R.id.edtSubjectRemark);
        this.mBtnSubjectCreate = (Button) findViewById(R.id.btnSubjectCreate);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.revSubjectList);
    }

    //endregion

    /**
     * 载入时发生
     *
     * @param savedInstanceState
     */
    private void Load(Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = getIntent().getBundleExtra("sendBookType");
        String remark = bundle.getString("remark").trim();
        mTxvSubjectTitle.setText(bundle.getString("name") + (remark .length() ==0 ?"":"【" +remark + "】"));
        accountBookID = bundle.getLong("book_ID");
        showSubjectList();
    }

    /**
     * 创建一个记账科目
     *
     * @param view
     */
    private void createOneSubject(View view)
    {
        boolean isout = mRdbOut.isChecked();
        ACCOUNTBOOK accountbook = ACCOUNTBOOK.getItSelf(accountBookID);
        String name = mEdtSubjectName.getText().toString();
        String remark = mEdtSubjectRemark.getText().toString();
        if(name .trim().length() ==0)
        {
            Toast.makeText(this, "记账科目的名称必须填写！", Toast.LENGTH_LONG).show();
            return ;
        }
        IModelHelper temp = new ACCOUNTSUBJECT(accountbook, name, remark, isout);
        if (temp._Insert() == -1)
        {
            Toast.makeText(this, "记账科目重复", Toast.LENGTH_LONG).show();
            return;
        } else
        {
            doSuccess();
            Toast.makeText(this, "成功！", Toast.LENGTH_LONG).show();
        }
        showSubjectList();
    }

    private void RbdInChecked(CompoundButton compoundButton, boolean b)
    {
        mRdbOut.setChecked(!b);
    }

    private void RbdOutChecked(CompoundButton compoundButton, boolean b)
    {
        mRdbIn.setChecked(!b);
    }


    private void showSubjectList()
    {
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(accountBookID);
        List<String> mDatasName = new ArrayList<String>();
        List<String> mDatasRemark = new ArrayList<String>();
        List<String> mDatasOut = new ArrayList<String>();
        for (ACCOUNTSUBJECT temp : list)
        {
            String outin = temp.getISOUT() ? "支出" : "收入";
            mDatasOut.add(outin);
            mDatasName.add(Toolkit .replaceBlank(temp.getNAME()));
            mDatasRemark.add(Toolkit .replaceBlank(temp.getREMARK()));
        }
        SubjectItmeAdapter mAdapter = new SubjectItmeAdapter(mDatasName, mDatasRemark, mDatasOut, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    void doSuccess()
    {
        mEdtSubjectRemark.setText("");
        mEdtSubjectName.setText("");
    }






}
