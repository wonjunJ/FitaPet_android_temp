package com.example.fitapet.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fitapet.Cookie
import com.example.fitapet.MainActivity
import com.example.fitapet.databinding.ActivityLoginBinding
import com.example.fitapet.retrofit.RetrofitClient.apiServer
import com.example.fitapet.retrofit.dto.KakaoUser
import com.example.fitapet.retrofit.dto.getCurrentServiceDTO
import com.example.fitapet.retrofit.dto.getCurrentServiceDtoNoResult
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var keyHash = Utility.getKeyHash(this)
        Log.e("Hash", keyHash)
        binding.kakaoLoginButton.setOnClickListener {
            //카카오톡 설치 여부
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오계정으로 로그인
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "로그인 실패", error)
                    } else if (token != null) {
                        Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken} ")
                        //카카오 response 테스트
                        // 사용자 정보 요청 (기본)
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("TAG11", "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.i(
                                    "TAG11", "사용자 정보 요청 성공" +
                                            "\n유저: ${user}" +
                                            "\n회원번호: ${user.id}" +
                                            "\n이메일: ${user.kakaoAccount?.email}" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                                            "\n성별: ${user.kakaoAccount?.gender}"
                                )
                                val kakaoUser = KakaoUser(
                                    user.id,
                                    user.kakaoAccount?.profile?.nickname,
                                    user.kakaoAccount?.profile?.thumbnailImageUrl,
                                    user.kakaoAccount?.email,
                                    user.kakaoAccount?.gender.toString()
                                )
                                //쿠키에 회원ID담기
                                Cookie.userId=user.id
                                val responseHasId = apiServer.getHasId(kakaoUser.userId)
                                responseHasId.enqueue(object : Callback<getCurrentServiceDTO> {
                                    override fun onResponse(
                                        call: Call<getCurrentServiceDTO>,
                                        response: Response<getCurrentServiceDTO>
                                    ) {
                                        if (response.body()?.code == 1000) {
                                            Log.d("TAG11", "아이디 존재")
                                            Log.d("TAG11",response.headers().toString())
                                        } else if (response.body()?.code == 1001) {
                                            Log.d("TAG11", "아이디 없음")
                                            val responseSignUp = apiServer.signUp(kakaoUser)
                                            responseSignUp.enqueue(object :
                                                Callback<getCurrentServiceDtoNoResult> {
                                                override fun onResponse(
                                                    call: Call<getCurrentServiceDtoNoResult>,
                                                    response: Response<getCurrentServiceDtoNoResult>
                                                ) {
                                                    if (response.body()?.code == 1000) {
                                                        Log.d("TAG11", "회원가입성공")
                                                        val responseHasId2 = apiServer.getHasId(kakaoUser.userId)
                                                    } else {
                                                        Log.d(
                                                            "TAG11",
                                                            "이유불문: " + response.body()?.code + "  " + response.code()
                                                        )
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<getCurrentServiceDtoNoResult>,
                                                    t: Throwable
                                                ) {
                                                    Log.d("TAG11", "회원가입api실패")
                                                }

                                            })
                                        } else {
                                            Log.d("TAG11", "이유불문" + response.body()?.code)
                                        }


                                    }

                                    override fun onFailure(
                                        call: Call<getCurrentServiceDTO>,
                                        t: Throwable
                                    ) {
                                        Log.d("TAG11", "연결실패")
                                    }
                                })

                            }
                        }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "로그인 실패", error)
                    } else if (token != null) {
                        Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken}")

                        Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken} ")
                        //카카오 response 테스트
                        // 사용자 정보 요청 (기본)
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e("TAG11", "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.i(
                                    "TAG11", "사용자 정보 요청 성공" +
                                            "\n유저: ${user}" +
                                            "\n회원번호: ${user.id}" +
                                            "\n이메일: ${user.kakaoAccount?.email}" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                                            "\n성별: ${user.kakaoAccount?.gender}"
                                )
                                val kakaoUser = KakaoUser(
                                    user.id,
                                    user.kakaoAccount?.profile?.nickname,
                                    user.kakaoAccount?.profile?.thumbnailImageUrl,
                                    user.kakaoAccount?.email,
                                    user.kakaoAccount?.gender.toString()
                                )
                                //쿠키에 회원ID담기
                                Cookie.userId=user.id
                                val responseHasId = apiServer.getHasId(kakaoUser.userId)
                                responseHasId.enqueue(object : Callback<getCurrentServiceDTO> {
                                    override fun onResponse(
                                        call: Call<getCurrentServiceDTO>,
                                        response: Response<getCurrentServiceDTO>
                                    ) {
                                        if (response.body()?.code == 1000) {
                                            Log.d("TAG11", "아이디 존재")
                                        } else if (response.body()?.code == 1001) {
                                            Log.d("TAG11", "아이디 없음")
                                            val responseSignUp = apiServer.signUp(kakaoUser)
                                            responseSignUp.enqueue(object :
                                                Callback<getCurrentServiceDtoNoResult> {
                                                override fun onResponse(
                                                    call: Call<getCurrentServiceDtoNoResult>,
                                                    response: Response<getCurrentServiceDtoNoResult>
                                                ) {
                                                    if (response.body()?.code == 1000) {
                                                        Log.d("TAG11", "회원가입성공")
                                                        val responseHasId2 = apiServer.getHasId(kakaoUser.userId)

                                                    } else {
                                                        Log.d(
                                                            "TAG11",
                                                            "이유불문: " + response.body()?.code + "  " + response.code()
                                                        )
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<getCurrentServiceDtoNoResult>,
                                                    t: Throwable
                                                ) {
                                                    Log.d("TAG11", "회원가입api실패")
                                                }

                                            })
                                        } else {
                                            Log.d("TAG11", "이유불문" + response.body()?.code)
                                        }


                                    }

                                    override fun onFailure(
                                        call: Call<getCurrentServiceDTO>,
                                        t: Throwable
                                    ) {
                                        Log.d("TAG11", "연결실패")
                                    }
                                })

                            }
                        }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("로그", "카카오 로그인이 불가합니다.")
                    }
                }
            }
        }
    }
}