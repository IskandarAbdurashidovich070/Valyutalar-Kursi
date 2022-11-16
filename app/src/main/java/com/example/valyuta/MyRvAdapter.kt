package com.example.valyuta

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.valyuta.databinding.DialogItemBinding
import com.example.valyuta.databinding.RvItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class MyRvAdapter(var list: List<Valyuta>, var context: Context) : RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(var rvItemBinding: RvItemBinding):RecyclerView.ViewHolder(rvItemBinding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(user: Valyuta, position: Int ){
            rvItemBinding.tv1.text = user.CcyNm_EN
            rvItemBinding.tv2.text = user.Rate
            var diff = user.Diff.toDouble()

            if (diff == 0.0){
                rvItemBinding.image.setImageResource(R.drawable.balance)
            }else if (diff >= 0.0){
                rvItemBinding.image.setImageResource(R.drawable.growth)
            }else{
                rvItemBinding.image.setImageResource(R.drawable.dollar)
            }

            rvItemBinding.diff.text = user.Diff


            rvItemBinding.back.setOnClickListener {
                val dialog = AlertDialog.Builder(context).create()
                val dialogItemBinding = DialogItemBinding.inflate(LayoutInflater.from(context))
                dialog.setView(dialogItemBinding.root)
                dialogItemBinding.name.text = list[position].Ccy
                dialogItemBinding.count.text = list[position].Rate + "so'm"
                dialogItemBinding.news.text  = "Oxirgi Yangilanish:" + list[position].Date
                dialogItemBinding.btn.setOnClickListener {
                    dialog.cancel()
                }
                dialog.setCancelable(false)
                dialog.show()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context) , parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}