package test.bwei.com.erjicart;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.ls.LSException;

import java.util.List;

/**
 * Created by C on 2017/10/25.
 */

public class FatherAdapter extends RecyclerView.Adapter<FatherAdapter.myViewHolder> {
    private List<GoodsInfo> list;
    private Context context;

    public FatherAdapter(List<GoodsInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fatheritem, parent, false);
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        final GoodsInfo goodsInfo = list.get(position);
        isAllChecked(holder, goodsInfo);
        holder.seller.setText(list.get(position).seller);
        final ChildAdapter childAdapter = new ChildAdapter(list.get(position).list, context);
        holder.flv.setLayoutManager(new LinearLayoutManager(context));
        holder.flv.setAdapter(childAdapter);
        childAdapter.setPostionListener(new ChildAdapter.PostionListener() {
            @Override
            public void ItemPostion(int cpos) {
                isAllChecked(holder, goodsInfo);
                fatherListener.FatherClick(holder.getLayoutPosition(), cpos);
            }
        });
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checked = holder.cb.isChecked();
                if (checked) {
                    fatherListener.CLick(holder.getLayoutPosition(),true);
                    for (Goods goods : goodsInfo.list) {
                        goods.status = true;
                    }

                } else {
                    fatherListener.CLick(holder.getLayoutPosition(),false);
                    for (Goods goods : goodsInfo.list) {
                        goods.status = false;
                    }

                }
               notifyDataSetChanged();

            }
        });
    }

    public void isAllChecked(myViewHolder holder, GoodsInfo goodsInfo) {
        boolean flag = true;
        for (Goods goods : goodsInfo.list) {
            if (!goods.status) {
                flag = false;
            }
        }
        holder.cb.setChecked(flag);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private final TextView seller;
        private final CheckBox cb;
        private final RecyclerView flv;

        public myViewHolder(View itemView) {
            super(itemView);
            seller = itemView.findViewById(R.id.ftv);
            cb = itemView.findViewById(R.id.fcb);
            flv = itemView.findViewById(R.id.flv);
        }
    }

    private FatherListener fatherListener;

    public void setFatherListener(FatherListener fatherListener) {
        this.fatherListener = fatherListener;
    }

    public interface FatherListener {
        void FatherClick(int fpos, int cpos);

        void CLick(int fpos,boolean bb);
    }

}
