package test.bwei.com.erjicart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rlv;
    private CheckBox cb;
    private TextView sum;
    private List<GoodsInfo> goodsInfos;
    private int sunprice;
    private FatherAdapter fatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        goodsInfos = new ArrayList<>();
        GoodsInfo goodsInfo = null;
        for (int i = 0; i < 5; i++) {
            goodsInfo = new GoodsInfo();
            goodsInfo.seller = "商家" + i;
            Goods goods = null;
            goodsInfo.list = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                goods = new Goods();
                goods.price = 100 + 10 * j;
                goods.title = "商品" + i;
                goods.status = false;
                goods.num=1;
                goodsInfo.list.add(goods);
            }
            goodsInfos.add(goodsInfo);
        }

        fatherAdapter = new FatherAdapter(goodsInfos, this);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(fatherAdapter);
        fatherAdapter.setFatherListener(new FatherAdapter.FatherListener() {
            @Override
            public void FatherClick(int fpos, int cpos) {

                if (goodsInfos.get(fpos).list.get(cpos).status) {
                    sunprice = sunprice + goodsInfos.get(fpos).list.get(cpos).price*goodsInfos.get(fpos).list.get(cpos).num;
                } else {
                    sunprice = sunprice - goodsInfos.get(fpos).list.get(cpos).price*goodsInfos.get(fpos).list.get(cpos).num;
                }
                isAllChecked();
                sum.setText(sunprice + "");
            }

            @Override
            public void CLick(int fpos, boolean bb) {

                if (bb) {
                    for (Goods goods : goodsInfos.get(fpos).list) {
                        if (!goods.status) {
                            goods.status=true;
                            sunprice = sunprice + goods.price*goodsInfos.get(fpos).list.get(fpos).num;
                        }
                    }
                    sum.setText(sunprice + "");
                    isAllChecked();
                } else {
                    for (Goods goods : goodsInfos.get(fpos).list) {
                        if (goods.status) {
                            goods.status=false;
                            sunprice = sunprice - goods.price*goodsInfos.get(fpos).list.get(fpos).num;
                        }
                    }
                    sum.setText(sunprice + "");
                    isAllChecked();
                }
                isAllChecked();
            }


        });
    }


    private void initView() {
        rlv = (RecyclerView) findViewById(R.id.rlv);
        cb = (CheckBox) findViewById(R.id.cba);
        sum = (TextView) findViewById(R.id.sum);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (GoodsInfo goodsInfo : goodsInfos) {
                    for (Goods goods : goodsInfo.list) {
                        if(cb.isChecked()){
                            if(!goods.status){
                                goods.status=true;
                                sunprice=sunprice+goods.price*goods.num;
                            }
                        }else{
                            if(goods.status){
                                goods.status=false;
                                sunprice=sunprice-goods.price*goods.num;
                            }
                        }

                    }
                }
                sum.setText(sunprice+"");
                fatherAdapter.notifyDataSetChanged();
            }
        });
    }

    public void isAllChecked() {
        boolean flag=true;
        for (GoodsInfo goodsInfo : goodsInfos) {

            for (Goods goods : goodsInfo.list) {
                if(!goods.status){
                    flag=false;
                }
            }
        }
        cb.setChecked(flag);
    }

}
