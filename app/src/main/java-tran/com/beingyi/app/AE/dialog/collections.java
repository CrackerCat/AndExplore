package com.beingyi.app.AE.dialog;

import com.beingyi.app.AE.application.MyApplication;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beingyi.app.AE.R;
import com.beingyi.app.AE.bean.PathInfo;
import com.beingyi.app.AE.ui.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class collections extends baseDialog {

    List<PathInfo> list;

    List<String> pathList = new ArrayList<>();

    public void initdata(){
        pathList.clear();
        for (int i = 0; i < list.size(); i++) {
            pathList.add(list.get(i).getPath());
        }

    }

    public collections(Context mContext, int mWindow, String mPath) {
        super(mContext, mWindow, mPath);

        list=utils.getPaths();

        if(list==null){
            ToastUtils.show(MyApplication.getInstance().getString("fa8f67df27cd2844a66103b93f9ce900"));
            return;
        }

        initdata();

        MyAdapter myAdapter=new MyAdapter(context,pathList);
        final ListView listView = new ListView(context);
        listView.setAdapter(myAdapter);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(MyApplication.getInstance().getString("46779389fd987dbf051faee01df3bfaf"))
                .setView(listView)
                .setCancelable(false)
                .setPositiveButton(MyApplication.getInstance().getString("b15d91274e9fc68608c609999e0413fa"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .create();
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String path = pathList.get(position);
                File file = new File(path);
                if(file.isFile()) {
                    adapter.getFiles(file.getParent());
                    adapter.setItemHighLight(file.getAbsolutePath());

                }else{
                    adapter.getFiles(file.getAbsolutePath());
                }
                dialog.dismiss();
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                String value=list.get(position).getPath();
                utils.removePath(value);
                list.clear();
                list=utils.getPaths();
                initdata();
                myAdapter.notifyDataSetChanged();
                return true;
            }
        });



    }











    private class MyAdapter extends BaseAdapter {


        Context context;
        List<String> pathList;

        boolean isChoosed = false;

        public MyAdapter(final Context context,List<String> mPathList) {
            this.context = context;
            this.pathList = mPathList;

        }
        

        @Override
        public int getCount() {
            if (pathList != null && pathList.size() > 0) {
                return pathList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (pathList != null && pathList.size() > 0) {
                return pathList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        public List<String> getData() {
            return pathList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_simple, null);
                holder = new Holder();

                holder.Name = (TextView) convertView.findViewById(R.id.item_simple_TextView);

                convertView.setTag(holder);


            } else {
                holder = (Holder) convertView.getTag();
            }


            String Name = pathList.get(position);

            holder.Name.setText(Name);
            return convertView;
        }
        
        
    }


    class Holder{
        TextView Name;
    }



}
