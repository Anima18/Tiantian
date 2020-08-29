package com.chris.tiantian.module.message.fragment;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anima.componentlib.paginglistview.PagingRecycleAdapter;
import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.anima.componentlib.paginglistview.viewholder.PagingRecycleItemViewHolder;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataListCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.MessageItemViewHolder;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.dataparser.ListStatusDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.DateUtil;
import com.chris.tiantian.util.LocationLog;
import com.chris.tiantian.util.UserUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by jianjianhong on 20-7-30
 */
public class StrategyMessageFragment extends Fragment {
    private View rootView;
    private PagingRecycleView recycleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_strategy, container, false);
            recycleView = rootView.findViewById(R.id.strategyMessage_listView);
            intiRecycleView();
        }
        return rootView;
    }

    private void intiRecycleView() {
        recycleView.setItemViewHolderCreator(new PagingRecycleAdapter.OnItemViewHolderCreator() {
            @Override
            public PagingRecycleItemViewHolder create(View itemView) {
                return new MessageItemViewHolder(getContext(), itemView);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationLog.getInstance().i("StrategyMessageFragment request data");
        requestData();
    }

    private void requestData() {
        recycleView.setPageable(false);
        recycleView.bindDataSource((startNo, endNo, page, callback) -> {
            getNetworkData(callback);
        });


    }

    public void getNetworkData(PagingRecycleView.LoadCallback loadCallback) {
        if(!UserUtil.isLogin()) {
            recycleView.getCallback().onError( "请先登录！");
            return;
        }
        String lastTime = DateUtil.formatDate(DateUtil.getPastDay(15), Constant.DATA_TIME_FORMAT);
        LocationLog.getInstance().i("StrategyMessageFragment request StrategyMessage at "+lastTime);
        lastTime = Base64.encodeToString(lastTime.getBytes(), Base64.DEFAULT);
        String url = String.format("%s/comment/apiv2/policySubscribedSignalList/%s/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId(), lastTime);
        new NetworkRequest<PolicySignal>(getContext())
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(PolicySignal.class)
                .dataParser(new ListStatusDataParser<PolicySignal>())
                .getList(new DataListCallback<PolicySignal>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                        LocationLog.getInstance().i("StrategyMessageFragment request error: "+s);
                        loadCallback.onError(s);
                    }

                    @Override
                    public void onSuccess(@NotNull List<? extends PolicySignal> list) {
                        LocationLog.getInstance().i("StrategyMessageFragment request success");
                        if(list.size() < 60) {
                            loadCallback.onResult(list);
                        }else {
                            loadCallback.onResult(list.subList(0, 60));
                        }

                    }
                });
    }

}
