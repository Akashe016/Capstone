package com.example.simpleinvoicegenerationapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class DashBoardAdapter(private val list: List<User>) :
    RecyclerView.Adapter<DashBoardAdapter.DashBoardView>(){

    class DashBoardView (val view : View) : RecyclerView.ViewHolder(view){

            val clientName : TextView = view.findViewById(R.id.clientName)
            val clientINR : TextView = view.findViewById(R.id.clientINR)
            val clientDate : TextView = view.findViewById(R.id.clientDate)
            val editButton : ImageView = view.findViewById(R.id.editButton)
            val touchData :ConstraintLayout = view.findViewById(R.id.touchData)
        }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardView {

        val viewPage = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_activity, parent, false)

        return DashBoardView(viewPage)
    }

    override fun onBindViewHolder(holder: DashBoardView, position: Int) {

        val user = list[position]

        holder.clientName.text = user.clientName
        holder.clientINR.text = user.amount.toString()
        holder.clientDate.text = user.date

            val context = holder.itemView.context

            holder.editButton.setOnClickListener {
                val intent = Intent(context, EditInvoice::class.java).apply {

                    putExtra("USER_ID", user.id)
                    putExtra("CLIENT_NAME", user.clientName)
                    putExtra("AMOUNT", user.amount)
                    putExtra("DATE", user.date)
                    putExtra("DESCRIPTION", user.description)
                }
                context.startActivity(intent)
            }

            holder.touchData.setOnClickListener {
                val intent = Intent(context, DeleteInvoice::class.java).apply {

                    putExtra("USER_ID", user.id)
                    putExtra("CLIENT_NAME", user.clientName)
                    putExtra("AMOUNT", user.amount)
                    putExtra("DATE", user.date)
                    putExtra("DESCRIPTION", user.description)
                }
                context.startActivity(intent)
            }
    }
}
