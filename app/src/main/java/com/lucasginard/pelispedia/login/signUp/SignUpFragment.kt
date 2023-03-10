package com.lucasginard.pelispedia.login.signUp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucasginard.pelispedia.R
import com.lucasginard.pelispedia.databinding.FragmentSignUpBinding
import com.lucasginard.pelispedia.home.HomeActivity
import com.lucasginard.pelispedia.login.signUp.presenter.SignUpContract
import com.lucasginard.pelispedia.login.signUp.presenter.SignUpPresenter
import com.lucasginard.pelispedia.utils.SessionCache

class SignUpFragment : Fragment(),SignUpContract.View {

    lateinit var _binding: FragmentSignUpBinding
    lateinit var presenter: SignUpPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        presenter = SignUpPresenter()
        configureOnClickListener()
        configureSaveCheck()
        return _binding.root
    }

    private fun configureSaveCheck() {
        val user = presenter.getSaveUser()
        if (user != null){
            _binding.editPassword.setText(user.password)
            _binding.editUser.setText(user.username)
            _binding.rememberMe.isChecked = user.check
        }
    }

    private fun configureOnClickListener(){
        _binding.btnLogin.setOnClickListener {
            validateLogin()
        }

        _binding.rememberMe.setOnCheckedChangeListener { compoundButton, b ->
            if (!b){
                presenter.deleteUserSave()
            }
        }
    }

    private fun validateLogin(){
        try {
            if (presenter.validateInputs(_binding.editUser.text.toString(),_binding.editPassword.text.toString())){
                SessionCache.clear()
                presenter.saveUserData(_binding.editUser.text.toString(),_binding.editPassword.text.toString(),_binding.rememberMe.isChecked)
                activity?.startActivity(Intent(activity, HomeActivity::class.java))
            }else{
                showError()
            }
        }catch (e:Exception){
            showError()
        }
    }

    override fun showError() {
        _binding.editUserContainer.error = activity?.getString(R.string.errorValidate) ?: ""
        _binding.editPasswordContainer.error = activity?.getString(R.string.errorValidate) ?: ""
        _binding.editPasswordContainer.errorIconDrawable = null
    }

    override fun hideError() {
        _binding.editUser.isEnabled = true
        _binding.editPassword.isEnabled = true
    }
}