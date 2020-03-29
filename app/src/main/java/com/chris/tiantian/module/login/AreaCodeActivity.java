package com.chris.tiantian.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.tiantian.R;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.view.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;

public class AreaCodeActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_code);
        recycleView = findViewById(R.id.areaCodeAct_list);
        intiRecycleView();
    }

    private void intiRecycleView() {
        CommonAdapter<String> adapter = new CommonAdapter<>(this, R.layout.listview_area_code);
        adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
            @Override
            public CommonItemViewHolder create(View itemView) {
                return new AreaCodeViewHolder(itemView);
            }
        });
        List<String> datas = Arrays.asList(getResources().getStringArray(R.array.country_code_list_zh));
        adapter.setData(datas);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                String[] array = ((String)item).split("\\+");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("area_code", "+"+array[1]);
                setResult(RESULT_OK, resultIntent);
                AreaCodeActivity.this.finish();
            }
        });

        recycleView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
        recycleView.setLayoutManager(layoutManager);
    }

    class AreaCodeViewHolder extends CommonItemViewHolder<String> {
        TextView nameTextView;
        TextView codeTextView;
        public AreaCodeViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.area_name);
            codeTextView = itemView.findViewById(R.id.area_code);
        }

        @Override
        public void bindto(@NonNull String data) {
            String[] dataArray = data.split("\\+");
            nameTextView.setText(dataArray[0]);
            codeTextView.setText("+"+dataArray[1]);
        }
    }
}
