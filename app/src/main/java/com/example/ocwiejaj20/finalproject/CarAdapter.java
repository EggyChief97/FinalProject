package com.example.ocwiejaj20.finalproject;

/**
 * Created by ocwiejaj20 on 5/24/2019.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.BookViewHolder> {
    private List<Car> carList;

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mCar;
        private Car mSubModel;
        private Context mContext;

        public BookViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mCar = (TextView) itemView.findViewById(R.id.cars);
        }

        @Override
        public void onClick(View view) {
            Uri carUri = Uri.parse(mSubModel.getCarModel());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, carUri);
            mContext.startActivity(websiteIntent);
        }


        public void bindBook(Car car) {
            mSubModel = car;
            String title = mSubModel.getCarModel();
            mCar.setText(title);

        }
    }

    public CarAdapter(List<Car> bookList) {
        this.carList = bookList;
    }

    public void setData(List<Car> bookList) {
        this.carList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listofsearchedcars, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Car book = carList.get(position);
        holder.bindBook(book);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }
}

