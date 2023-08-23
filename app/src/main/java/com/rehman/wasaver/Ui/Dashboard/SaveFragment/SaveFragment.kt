package com.rehman.wasaver.Ui.Dashboard.SaveFragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rehman.wasaver.R
import com.rehman.wasaver.Ui.Dashboard.SaveFragment.Adapter.FileListAdapter
import com.rehman.wasaver.databinding.FragmentSaveBinding
import java.io.File

class SaveFragment : Fragment() {

    private lateinit var binding: FragmentSaveBinding

    private lateinit var list: ArrayList<File>
    private lateinit var fileListAdapter: FileListAdapter

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveBinding.bind(view)

        initViews()
        swipeListner()


    }

    private fun swipeListner() {
        binding.swipeRefresh.setOnRefreshListener {

            try {
                createFileFolder()

                getAllFiles()

            } catch (e: Exception) {
                e.printStackTrace()
            }


            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun initViews() {


        val textPaint = binding.titleSave.paint
        val width = textPaint.measureText("    Images")

        val textShader = LinearGradient(
            0f, 0f, width, binding.titleSave.textSize,
            intArrayOf(
                Color.parseColor("#3bc1e6"),
                Color.parseColor("#ce38ce")
            ),
            null,
            Shader.TileMode.CLAMP
        )
        binding.titleSave.paint.shader = textShader


        list = ArrayList()

        checkPermissions(0)

        try {
            createFileFolder()

            getAllFiles()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (list.size == 0) {
            binding.saveNoResult.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.saveNoResult.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = true
        }
        binding.swipeRefresh.isRefreshing = false
    }


    private fun checkPermissions(type: Int): Boolean {
        var result: Int
        val listPermissionNeeded: MutableList<String> = java.util.ArrayList()

        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(requireContext(), p)

            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(p)
            }
        }

        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                (activity as Activity?)!!,
                listPermissionNeeded.toTypedArray(), type
            )

            return false
        } else {

            try {
                createFileFolder()

                getAllFiles()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return true
    }

    private fun getAllFiles() {
        list = ArrayList()

        val fileLocation: File = File(
            "${Environment.getExternalStorageDirectory()}/Documents/WA Saver/"
        )
        val files: Array<File> = fileLocation.listFiles()!!

        for (file in files) {
            list.add(file)
        }

        fileListAdapter = FileListAdapter(list, requireContext())


        binding.rvSave.setHasFixedSize(true)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rvSave.layoutManager = manager
        binding.rvSave.adapter = fileListAdapter


    }

    private fun createFileFolder() {

        if (!File("${Environment.getExternalStorageDirectory()}/Documents/WA Saver/").exists()) {

            File("${Environment.getExternalStorageDirectory()}/Documents/WA Saver/").mkdir().apply {


            }
        }
    }
}