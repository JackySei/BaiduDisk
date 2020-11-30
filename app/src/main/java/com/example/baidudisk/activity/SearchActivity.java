package com.example.baidudisk.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baidudisk.db.DBHelper;
import com.example.baidudisk.R;
import com.example.baidudisk.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnTouchListener {
    private final static String TABLE_NAME="search_history";
    private final static String COLUMN_NAME="name";
    private static  String DB_NAME ="history.db";
    private static  int DB_VERSION =1 ;
    TextView cancel_serach;
    EditText mEtSearch;
    SearchAdapter searchAdapter;
    RecyclerView recyclerView;
    TextView deleteAll;
    Drawable delete;
    private List<String> historyList;
    SQLiteDatabase mDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView=findViewById(R.id.rv_search);
        cancel_serach=findViewById(R.id.cancel_search);
        mEtSearch=findViewById(R.id.et_search);
        historyList=new ArrayList<>();
        deleteAll=findViewById(R.id.deleteAll);
        searchAdapter=new SearchAdapter(historyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
        queryAllHistory();
        delete= ContextCompat.getDrawable(this,R.mipmap.deleteall);
        delete.setBounds(0,0,delete.getIntrinsicWidth()*3/4,delete.getIntrinsicHeight()*3/4);
        searchAdapter.setClick(new SearchAdapter.Click() {
            @Override
            public void Delete(int position) {
                deleteHistory(historyList.get(position));
                historyList.remove(historyList.get(position));
                 searchAdapter.notifyDataSetChanged();
            }
            @Override
            public void AddText(int position) {
                mEtSearch.setText(historyList.get(position));
                mEtSearch.setSelection(mEtSearch.getText().length());
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteALLHistory();
                historyList.clear(); //清除记录
                searchAdapter.notifyDataSetChanged();
            }
        });
        mEtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        cancel_serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String str= cancel_serach.getText().toString();
                if(str.equals("搜索")){
                    search();
                   // historyList.add(new SearchEnity(mEtSearch.getText().toString()));
                   searchAdapter.notifyDataSetChanged();
                }else{
                    finish();
                }
            }
        });
        mEtSearch.setOnTouchListener(this);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDelete();
            }
        });


    }

    public void queryAllHistory(){
        mDB=new DBHelper(this,DB_NAME,null,DB_VERSION)
                .getWritableDatabase();
        Cursor cursor=mDB.query(TABLE_NAME,new String[]{COLUMN_NAME},null,null,null,null,null);
        while (cursor.moveToNext()){
            historyList.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        }
        cursor.close();
      searchAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param name 需要插入的字符串
     */
    private void insertHistory(String name){
        //通过键值对插入数据项
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,name);
        mDB.insert(TABLE_NAME,null,values);
    }

    /**
     *
     * @param name 需要删除的字符串
     */
    private void deleteHistory(String name){
        mDB.delete(TABLE_NAME,COLUMN_NAME+"=?",new String[]{name});
    }

    private void deleteALLHistory(){
        mDB.execSQL("delete from "+TABLE_NAME);
    }

    /**
     * 搜索
     * 向搜索历史添加数据
     */
    private void search(){
        String content =mEtSearch.getText().toString();
        if (content.length()>0&&!historyList.contains(content)){
            historyList.add(content);
            searchAdapter.notifyDataSetChanged();
            insertHistory(content);
        }
    }
    private void setDelete() {
        if(mEtSearch.getText().length()>0) {
            mEtSearch.setCompoundDrawables(null, null, delete, null);
            cancel_serach.setText("搜索");
        }
        else{
            mEtSearch.setCompoundDrawables(null,null,null,null);
            cancel_serach.setText("取消");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if  (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(event.getRawX()>v.getWidth()-v.getPaddingRight()-delete.getIntrinsicWidth())
            {
                mEtSearch.setText("");
            }
        }
         return false;
    }

}