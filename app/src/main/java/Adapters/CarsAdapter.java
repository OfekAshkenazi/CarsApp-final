package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.carsapp.R;

import java.util.ArrayList;

import Entities.Car;

/**
 * Created by Ofek on 18-Nov-17.
 */

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarVH> {
    ArrayList<Car> carsList;

    public CarsAdapter(ArrayList<Car> carsList) {
        this.carsList = carsList;
    }

    @Override
    public CarsAdapter.CarVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item_layout,parent,false);
        return new CarVH(view);
    }

    @Override
    public void onBindViewHolder(CarsAdapter.CarVH holder, int position) {
        Car car=carsList.get(position);
        holder.modelTV.setText(car.getModel());
        holder.yearTV.setText(""+car.getYear());
        holder.colorTV.setText(car.getColor());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public void setCarsList(ArrayList<Car> carsList) {
        this.carsList = carsList;
        notifyDataSetChanged();
    }

    public class CarVH extends RecyclerView.ViewHolder{
        TextView modelTV,yearTV,colorTV;
        public CarVH(View itemView) {
            super(itemView);
            modelTV=itemView.findViewById(R.id.modelTV_adapter);
            yearTV=itemView.findViewById(R.id.yearTV_adapter);
            colorTV=itemView.findViewById(R.id.colorTV_adapter);
        }
    }
}
