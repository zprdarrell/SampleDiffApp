package com.darrellgriffin.gitdiffapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.databinding.PullRecyclerItemBinding;
import com.darrellgriffin.gitdiffapp.model.PullRequest;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class RepoRecyclerAdapter extends RecyclerView.Adapter<RepoRecyclerAdapter.RepoViewHolder> {
    private PullRequestSelectedListener mListener;
    private final List<PullRequest> pullRequestList = new ArrayList<>();

    public RepoRecyclerAdapter(PullViewModel viewModel, LifecycleOwner owner, PullRequestSelectedListener listener){
        mListener = listener;
        viewModel.getPullList().observe(owner, pullList -> {
            pullRequestList.clear();
            if(pullList != null){
                pullRequestList.addAll(pullList);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(false);

    }
    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pull_recycler_item, parent, false);

        return new RepoViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(pullRequestList.get(position));
    }

    @Override
    public int getItemCount() {
        return pullRequestList.size();
    }

    public interface PullRequestSelectedListener{
        void onPullSelected(PullRequest request);
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder{
        private PullRequest pullRequest;
        PullRecyclerItemBinding binding;

        public RepoViewHolder(@NonNull View itemView, PullRequestSelectedListener listener) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(view -> {
                if(pullRequest != null)listener.onPullSelected(pullRequest);
            });
        }
        void bind(PullRequest request){
            this.pullRequest = request;
            binding.setSelectedPull(request);
        }
    }
}
