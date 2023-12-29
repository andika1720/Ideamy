package com.example.thefinalproject.ui.fragment.setting

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentMyProfileBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.getuser.Data
import com.example.thefinalproject.network.model.user.getuser.GetCurrentUser
import com.example.thefinalproject.network.model.user.updateprofile.DataUser
import com.example.thefinalproject.network.model.user.updateprofile.PutDataUser
import com.example.thefinalproject.network.model.user.updateprofile.ReqNewUser
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_settingFragment2)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil ID pengguna dari argumen
        val args = arguments?.getString("selectedId")
        val token = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        Log.d("FragmentTag", "selectedIdUser: $args")

        // Tampilkan profil pengguna
        detailUser(token.toString())

        binding.btnSaveProfile.setOnClickListener {
            val image = binding.ivEditImage.toString()
            val email = binding.tvEmailValue.text.toString()
            val name = binding.tvNamaValue.text.toString()
            val phoneNumber = binding.tvMobileValue.text.toString()
            val address = binding.tvAdressValue.text.toString()

            val newUser = ReqNewUser(
                address = address,

                email = email,
                encryptedPassword = null,
                image = image,
                name = name,
                phoneNumber = phoneNumber,
            )

            updateProfile(token,newUser)
            Toast.makeText(requireContext(),"Profile berhasil diubah",Toast.LENGTH_SHORT).show()
        }
    }

    private fun detailUser(token: String?) {
        viewModel.getCurrentUser(token).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showUser(data) }
                }
                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!!")
                }
                Status.LOADING -> {
                    Log.d("load", it.data.toString())
                }
            }
        }
    }

    private fun showUser(user: GetCurrentUser?) {
        if (user != null) {
            val data: Data? = user.data
            binding.tvEmailValue.text = Editable.Factory.getInstance().newEditable(data?.email.toString())
            binding.tvNamaValue.text = Editable.Factory.getInstance().newEditable(data?.name.toString())
            binding.tvMobileValue.text = Editable.Factory.getInstance().newEditable(data?.phoneNumber.toString())
            binding.tvAdressValue.text = Editable.Factory.getInstance().newEditable(data?.address.toString())
        } else {
            Log.d("Data Profile", "Profile tidak di temukan")
        }
    }


    private fun updateProfile(token: String?,user: ReqNewUser ){
        viewModel.updateProfile(token,user).observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    Log.d("Update Profile", "Success")
                    it.data?.let { data -> showUpdateProfile(data) }
                }
                Status.ERROR -> {
                    Log.d("Update Profile", "Error Occurred!!")
                }
                Status.LOADING -> {
                    Log.d("Update Profile", it.data.toString())
                }
            }
        }
    }

    private fun showUpdateProfile(data: PutDataUser) {
        val userData: DataUser? = data.data
        Glide.with(this)
            .load(userData?.image)
            .fitCenter()
            .into(binding.ivProfile)
        binding.tvEmailValue.text = Editable.Factory.getInstance().newEditable(userData?.email.toString())
        binding.tvNamaValue.text = Editable.Factory.getInstance().newEditable(userData?.name.toString())
        binding.tvMobileValue.text = Editable.Factory.getInstance().newEditable(userData?.phoneNumber.toString())
        binding.tvAdressValue.text = Editable.Factory.getInstance().newEditable(userData?.address.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
