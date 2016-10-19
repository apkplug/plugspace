package com.apkplug.baseplugmodle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apkplug.trust.PlugManager;
import com.apkplug.trust.common.listeners.OnGetRPCInstanceListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by qinfeng on 2016/10/17.
 */

public class PlugListAdapter extends RecyclerView.Adapter<PlugListAdapter.PlugViewHodler> {

    List<PlugListItem> plugitems;

    public void setPlugitems(List<PlugListItem> items){
        plugitems = items;
    }

    @Override
    public PlugViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plugitemview,parent,false);

        PlugViewHodler viewHodler = new PlugViewHodler(view);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(final PlugViewHodler holder, final int position) {
        final PlugListItem plugitem = plugitems.get(position);
        holder.plugName.setText(plugitem.plugName);
        plugitems.get(position).progressBar = holder.progressBar;
        holder.plugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.plugButton.getText().equals("Button")){
                    try {
                        plugitem.progressBar.setVisibility(View.VISIBLE);
                        getRpcInstances(plugitem,holder.plugButton);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }else if(holder.plugButton.getText().equals("可以运行")){

                    try {

                        Method method = plugitem.classInstance.getClass().getDeclaredMethod(plugitem.mothedName, plugitem.paramType);
                        method.invoke(plugitem.classInstance,plugitem.params);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return plugitems.size();
    }

    public  static class PlugViewHodler extends RecyclerView.ViewHolder{

        public TextView plugName;
        public ProgressBar progressBar;
        public Button plugButton;

        public PlugViewHodler(View itemView) {
            super(itemView);
            plugName = (TextView) itemView.findViewById(R.id.plugname);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            plugButton = (Button) itemView.findViewById(R.id.plugbutton);
        }
    }

    private <T> void getRpcInstances(final PlugListItem<T> listItem, final Button button) throws Throwable {

        PlugManager.getInstance().getRPCInstanceByShortLink(true,listItem.shortLink, listItem.rpcUri, listItem.rpcInterfaceClass, new OnGetRPCInstanceListener<T>() {
            @Override
            public void onDownloadProgress(String s, String s1, long l, long l1) {

                int percent = (int)((float)l/(float) l1*100);
                listItem.progressBar.setProgress(percent);
            }

            @Override
            public void onInstallSuccess(org.osgi.framework.Bundle bundle) {
                Log.e("install s",bundle.getName());
            }

            @Override
            public void onGetRPCSuccess(T t) {
                listItem.classInstance =t;
                button.setText("可以运行");
                listItem.progressBar.setVisibility(View.GONE);
                Log.e("rpc",t.toString());
            }

            @Override
            public void onFail(String s, String s1) {
                Log.e("fail",s+" "+s1);
                listItem.progressBar.setVisibility(View.GONE);
            }
        });

    }
}
