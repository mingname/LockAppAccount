package cn.app.lock.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.app.lock.com.R;


/**
 * Created by xueqiaoming on 2017/3/15.
 */

public class MapTypeAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<String>();
    private Context mContext;

    public MapTypeAdapter(List<String> list, Context mContext){
        super();
        this.list = list;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tpy_map_type,null);
            viewHolder = new ViewHolder();
            viewHolder.btnMapType = (Button) convertView.findViewById(R.id.btn_map_baidu);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String orderFormDetail = list.get(position);
        if(orderFormDetail != null){
            viewHolder.btnMapType.setText(orderFormDetail);
        }


        return convertView;
    }

    class ViewHolder{
        private Button btnMapType;
    }
}
