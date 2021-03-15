package com.beingyi.app.AE.utils.openFile;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.content.Context;
import com.beingyi.app.AE.activity.MainActivity;
import com.beingyi.app.AE.utils.AEUtils;
import com.beingyi.app.AE.adapter.FilesAdapter;
import android.view.View;
import com.beingyi.app.AE.ui.AlertProgress;
import com.beingyi.app.AE.utils.ZipTree;

public class s_base
{
    
    Context context;
    MainActivity activity;
    AEUtils utils;
    FilesAdapter adapter;
    View view;
    int window;
    boolean isInZip=false;

    public s_base(Context mContext,int mWindow,View mView,boolean mIsInZip){
        this.context=mContext;
        this.activity=(MainActivity)context;
        this.utils=new AEUtils(context);
        this.window=mWindow;
        this.adapter=activity.adapters.get(window);
        this.view=mView;
        this.isInZip=mIsInZip;
    }



    public void openZip(final String path){

        /**
         Intent intent=new Intent(context,ZipEditor.class);
         ZipEditor.zipFileName=file.getAbsolutePath();
         context.startActivity(intent);
         **/

        final AlertProgress progres=new AlertProgress(context);
        new Thread(){
            @Override
            public void run()
            {


                progres.setLabel(BASE128.decode("NG6/2bPeC5kiLGc6CtSQOA=="));
                progres.setNoProgress();
                progres.show();
                try
                {
                    adapter.zipTree = new ZipTree(path);
                    adapter.ListType = 2;

                    activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run()
                            {
                                adapter.refresh();
                            }
                        });
                }
                catch (Exception e)
                {
                    activity.showDialog(e.toString());
                }



                progres.dismiss();

            }
        }.start();


    }

    
    
}
