package com.rajibul.musicplayer

import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.rajibul.musicplayer.databinding.ActivityMainBinding
import com.rajibul.musicplayer.MusicContent
import com.rajibul.musicplayer.MusicViewModel

class MainActivity : AppCompatActivity() {
    var permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            //songs
        } else {

        }
    }
    var musicList = ArrayList<MusicContent>()
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var musicViewModel: MusicViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        navController = findNavController(R.id.navHost)
        binding.bottomView.setOnItemSelectedListener {
            System.out.println("it.itemId ${it.itemId}")
            if (it.itemId == R.id.Playlist) {
                navController.navigate(R.id.playlistFragment)
            } else if (it.itemId == R.id.PlayMusic) {
                navController.navigate(R.id.musicPlayingFragment)
            }
            return@setOnItemSelectedListener true
        }

    }
    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission
                (
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
              getSongs()
        } else {
            permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
    fun getSongs(){
        val uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection=MediaStore.Audio.Media.IS_MUSIC
        val cursor: Cursor?= contentResolver?.query(uri,null,selection,null,null)
        if(cursor !=null){if (cursor?.moveToFirst() == true) {
            while(cursor.isLast == false) {
                musicList.add(
                    MusicContent(
                        title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                        duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                        artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                        storagelocation = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    )
                )
                cursor.moveToNext()
            }
        }
            musicViewModel.musicContentList.value = musicList


        }
    }
}