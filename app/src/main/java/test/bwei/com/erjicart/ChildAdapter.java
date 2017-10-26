package test.bwei.com.erjicart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by C on 2017/10/25.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.myViewHolder> {
    private List<Goods> list;
    private Context context;

    public ChildAdapter(List<Goods> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.childitem, parent, false);
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        if(list.get(position).status){
            holder.cb.setChecked(true);
        }else{
            holder.cb.setChecked(false);
        }
        holder.goods.setText(list.get(position).title);
        holder.price.setText(list.get(position).price + "");
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = holder.cb.isChecked();
                list.get(position).status=checked;
                holder.amt.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                    @Override
                    public void onAmountChange(View view, int amount) {
                        list.get(position).num=amount;
                    }
                });
                postionListener.ItemPostion(position);
            }
        });
        holder.amt.setGoods_storage(10);
        holder.amt.setamount(list.get(position).num);
        holder.amt.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                list.get(position).num=amount;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cb;
        private final TextView goods;
        private final TextView price;
        private final AmountView amt;

        public myViewHolder(View itemView) {
            super(itemView);

            cb = itemView.findViewById(R.id.ccb);
            goods = itemView.findViewById(R.id.goods);
            price = itemView.findViewById(R.id.jiage);
            amt = itemView.findViewById(R.id.amt);
        }
    }

    public  PostionListener postionListener;

    public void setPostionListener(PostionListener postionListener) {
        this.postionListener = postionListener;
    }

    public interface PostionListener{
        void ItemPostion(int cpos);
    }
}
