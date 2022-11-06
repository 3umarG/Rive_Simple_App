package com.example.rivesimpleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.RiveInitializer
import app.rive.runtime.kotlin.core.Loop
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var button: Button
    private lateinit var inputLayout: TextInputLayout
    private var isShow = false
    private val animationView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<RiveAnimationView>(R.id.riveAnimation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRive()
        init()

        etPassword.setOnFocusChangeListener { _, isFocus ->
            isShow = if (isFocus) {
                // Hide the bear eyes ..
                animationView.play(animationName = BearStates.HANDS_UP.toString())
                false
            } else {
                // Show the bear eyes ..
                animationView.play(animationName = BearStates.HANDS_DOWN.toString())
                true
            }
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChangedRive(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        etEmail.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) {
                animationView.play(animationName = BearStates.LOOK_IDLE.toString())
            }
        }

        inputLayout.setEndIconOnClickListener {
            endIconToggle()
        }

        button.setOnClickListener {
            submit()
        }


    }

    private fun endIconToggle() {
        isShow = !isShow
        if (!isShow) {
            etPassword.transformationMethod = PasswordTransformationMethod()
        } else {
            etPassword.transformationMethod = null
        }
        changeVisibility(isShow)
    }

    private fun submit() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        animationView.play(animationName = BearStates.HANDS_DOWN.toString())

        if (email.isNotEmpty() && email.isNotBlank() && password.isNotEmpty() && password.isNotBlank() && password == "omar2002") {
            animationView.play(animationName = BearStates.SUCCESS.toString())
        } else {
            animationView.play(animationName = BearStates.FAIL.toString())
        }
    }

    private fun onTextChangedRive(s: String) {
        if (s.isBlank()) {
            animationView.play(animationName = BearStates.LOOK_IDLE.toString())
        }
        if (s.length <= 10) {
            animationView.play(animationName = BearStates.LOOK_DOWN_LEFT.toString())
        } else {
            animationView.play(animationName = BearStates.LOOK_DOWN_RIGHT.toString())
        }
    }

    private fun initRive() {
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(RiveInitializer::class.java)

        animationView.play(animationName = BearStates.IDLE_LOOP.toString(), loop = Loop.LOOP)

    }

    private fun changeVisibility(bool: Boolean) {
        if (bool) {
            animationView.play(animationName = BearStates.HANDS_DOWN.toString())
        } else {
            animationView.play(animationName = BearStates.HANDS_UP.toString())
        }
    }

    enum class BearStates {
        HANDS_UP, HANDS_DOWN,
        SUCCESS, FAIL,
        LOOK_DOWN_RIGHT, LOOK_DOWN_LEFT,
        LOOK_IDLE, IDLE_LOOP;

        override fun toString(): String {
            when (this) {
                HANDS_UP -> {
                    return "Hands_up"
                }
                HANDS_DOWN -> {
                    return "hands_down"
                }
                SUCCESS -> {
                    return "success"
                }
                FAIL -> {
                    return "fail"

                }
                LOOK_DOWN_RIGHT -> {
                    return "Look_down_right"

                }
                LOOK_DOWN_LEFT -> {
                    return "Look_down_left"

                }
                LOOK_IDLE -> {
                    return "look_idle"
                }
                IDLE_LOOP -> {
                    return "idle"
                }
            }
        }

    }

    private fun init() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        button = findViewById(R.id.sbmitButton)
        inputLayout = findViewById(R.id.textInput2)
    }

}