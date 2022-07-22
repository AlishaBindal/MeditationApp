package com.example.alishameditationapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.alishameditationapp.R
import com.example.alishameditationapp.data.model.*
import com.example.alishameditationapp.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var meditation: Meditation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromFirebaseDatabase()
        binding.bottomNavigation.setOnItemSelectedListener(this)

        binding.header.tvToolbarTitle.text = getString(R.string.text_meditate)
        binding.header.btnLogout.setOnClickListener {
            logout()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.meditate -> {
                binding.header.tvToolbarTitle.text = getString(R.string.text_meditate)
                val meditationData = meditation?.getMeditationData()?.get(0)
                openFragment(CommonFragment().newInstance(meditationData!!), false)
            }
            R.id.calm -> {
                binding.header.tvToolbarTitle.text = getString(R.string.text_calm)
                val meditationData = meditation?.getMeditationData()?.get(1)
                openFragment(CommonFragment().newInstance(meditationData!!), false)
            }
            R.id.destress -> {
                binding.header.tvToolbarTitle.text = getString(R.string.text_destress)
                val meditationData = meditation?.getMeditationData()?.get(2)
                openFragment(CommonFragment().newInstance(meditationData!!), false)
            }
            R.id.relax -> {
                binding.header.tvToolbarTitle.text = getString(R.string.text_relax)
                val meditationData = meditation?.getMeditationData()?.get(3)
                openFragment(CommonFragment().newInstance(meditationData!!), false)
            }
        }
        return true
    }


    private fun openFragment(fragment: Fragment, addToBackStack: Boolean) {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val backStackName = fragment.javaClass.canonicalName

            fragmentTransaction.replace(R.id.fragmentContainer, fragment, backStackName)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(backStackName)
            }

            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
        }
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
    }

    private fun getDataFromFirebaseDatabase(): Meditation? {
        binding.progressBar.visibility = View.VISIBLE
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Collections")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    Log.d("TAG", "Value is: $dataSnapshot")
                    meditation = dataSnapshot.getValue(Meditation::class.java)
                    openFragment(
                        CommonFragment().newInstance(meditation?.meditationData!![0]),
                        false
                    )
                    binding.progressBar.visibility = View.GONE
                    Log.d("TAG", "Value is: ${meditation.toString()}")
                } else {
                    setDataInFirebaseDatabase()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        return meditation
    }


    private fun setDataInFirebaseDatabase() {
        val meditationList = ArrayList<MeditationData>()

        val meditationData = MeditationData()
        meditationData.documentID = "focus"
        meditationData.dataField = DataField(324, "Focus")
        val link1 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/audio%2FAcceptance.mp3?alt=media&token=7ff1a857-24db-4483-9f45-65b398274799"
        val imageLink1 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/image%2FEcstasy.jpeg?alt=media&token=7d43b06d-15e1-47c2-8dcb-813d845f0176"
        val collectionData1 = CollectionData(imageLink1, link1)
        meditationData.subCollection = SubCollection("session1", collectionData1, "1")

        val calmData = MeditationData()
        calmData.documentID = "calm_down"
        calmData.dataField = DataField(621, "Calm Down")
        val link2 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/audio%2FDe-Stress.mp3?alt=media&token=711ccaf8-a3df-47b8-aa03-1489e622ec8c"
        val imageLink2 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/image%2FDe%20Stress.jpeg?alt=media&token=6f55dabd-2e31-4581-b5f1-af1a43308370"
        val collectionData2 = CollectionData(imageLink2, link2)
        calmData.subCollection = SubCollection("session2", collectionData2, "2")


        val destressData = MeditationData()
        destressData.documentID = "destress"
        destressData.dataField = DataField(212, "Destress")
        val link3 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/audio%2FLingashtakam.mp3?alt=media&token=20a5c53f-6956-4b2b-9bff-56e6a41d2798"
        val imageLink3 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/image%2FLingashtakam.jpg?alt=media&token=813ee6f5-5949-40a6-8b64-3d7aa97615d1"
        val collectionData3 = CollectionData(imageLink3, link3)
        destressData.subCollection = SubCollection("session3", collectionData3, "3")


        val relaxData = MeditationData()
        relaxData.documentID = "relax"
        relaxData.dataField = DataField(421, "Relax")
        val link4 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/audio%2FSounds%20of%20River.mp3?alt=media&token=1cc322c3-d347-476a-83a8-b7dbc05d5223"
        val imageLink4 =
            "https://firebasestorage.googleapis.com/v0/b/blissful-ly.appspot.com/o/image%2Fphoto-1445384763658-0400939829cd.jpg?alt=media&token=f8339591-778c-406e-a534-aca0142c0a0e"
        val collectionData4 = CollectionData(imageLink4, link4)
        relaxData.subCollection = SubCollection("session4", collectionData4, "4")

        meditationList.add(meditationData)
        meditationList.add(calmData)
        meditationList.add(destressData)
        meditationList.add(relaxData)

        val meditation = Meditation(meditationList)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("")
        myRef.child("Collections").setValue(meditation)

        getDataFromFirebaseDatabase()
    }

}