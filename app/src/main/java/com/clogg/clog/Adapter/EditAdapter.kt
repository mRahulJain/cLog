package com.clogg.clog.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.chatter.chatter.Database.AppDatabase
import com.clogg.clog.R
import kotlinx.android.synthetic.main.item_edit.view.*

class EditAdapter(val context: Context, val nameList : ArrayList<String>) :
    RecyclerView.Adapter<EditAdapter.NameViewHolder>() {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "SubjectName.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.item_edit, parent, false)
        return NameViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: EditAdapter.NameViewHolder, position: Int) {
        holder.itemView.subjectsED.text = nameList[position]!!.toString()
        holder.itemView.deleteSubjectED.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("${nameList[position]}")
                .setMessage("Delete this subject?")
                .setPositiveButton("Yes") { dialogInterface, which ->
                    db.SubjectNameDao().deleteSubject(nameList[position])
                    Toast.makeText(context,
                        "Deleted",
                        Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialogInterface, which ->
                    null
                }
            builder.create()
            builder.show()
        }
    }


    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}