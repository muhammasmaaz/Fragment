package com.example.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.play.core.integrity.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.regex.Pattern


@Suppress("DEPRECATION")
class RegisterFragment : Fragment(){

    private var checkmale: CheckBox?=null
    private var checkfemale: CheckBox?=null
    private var fragbtnregister: Button?=null
    private var FragedtxtFirstname: EditText?=null
    private var FragedtxtLastname: EditText?=null
    private var FragedtxtEmail: EditText?=null
    private var edttxtaddress:TextView?=null
    private var Fragedtxtviewdata: TextView?=null
    private var view: View?=null
    private var profileimageview: ImageView?=null
    private val captureImage = 1
    private val pickImage = 2
    private var getinputdata:String=""
    private var imagePathUri: Uri? = null
    var maleorfemale:String=""
    private lateinit var dbref :DatabaseReference


    private val PASSWORD_PATTERN: Pattern = Pattern.compile(


          "(?=.*[@])" +
                "(?=.*[a-z])(?=.*[A-Z])"+
                "(?=\\S+$)"+".{4,}"
    )

    private var password: EditText? = null
    private var matchpassword: EditText? = null

    private var imgbtnmapforaddress: ImageButton?=null
    private val imageUri:Uri?=null

    private val selectimage= registerForActivityResult(ActivityResultContracts.GetContent()){
        profileimageview?.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          view = inflater.inflate(R.layout.fragment_register, container, false)


        initUI()
        oncheckedlistner()
        onImageclicklistner()
        getinputdata=arguments?.getString("Location").toString()
        edttxtaddress?.text=getinputdata
        onMapclicklistener()

        onclicklistner()
        return view
    }

    private fun onMapclicklistener() {
        imgbtnmapforaddress?.setOnClickListener {

            val intent = Intent(activity,MapForAddress::class.java)
            startActivity(intent)

        }
    }

    //show the result like in these example show image in profile
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImage) {

            profileimageview?.setImageURI(data?.data)
            data?.data?.let { uploadprofileimage(it) }
            imagePathUri=data?.data


        }
        else if ( requestCode == captureImage) {
           profileimageview?.setImageBitmap(data?.getParcelableExtra<Bitmap>("data"))
            imagePathUri=data?.data
        }

    }

   //intilition
   private fun initUI() {
        checkmale = view?.findViewById<CheckBox>(R.id.checkMale)
        checkfemale = view?.findViewById<CheckBox>(R.id.checkFemale)
        fragbtnregister= view?.findViewById(R.id.FragbtnRegister)
        FragedtxtFirstname= view?.findViewById<EditText>(R.id.FirstName)
        FragedtxtLastname= view?.findViewById<EditText>(R.id.LastName)
       FragedtxtEmail= view?.findViewById<EditText>(R.id.Email)
       edttxtaddress= view?.findViewById<TextView>(R.id.edttxtaddressText)
        Fragedtxtviewdata=view?.findViewById<TextView>(R.id.txtGender)
        profileimageview=view?.findViewById(R.id.ProfileImageView)
       password=view?.findViewById<EditText>(R.id.edtxtTextPassword)
       matchpassword=view?.findViewById<EditText>(R.id.edtxtTextMatchPassword)
       imgbtnmapforaddress=view?.findViewById<ImageButton>(R.id.imgbtnmapforaddress)
       dbref= FirebaseDatabase.getInstance().getReference("User Data")
    }

   //onclicklistner for data show in edittext to textview
   @SuppressLint("SetTextI18n")
   private fun onclicklistner() {
        fragbtnregister?.setOnClickListener{
            if(imagePathUri!=null){
                uploadprofileimage(imagePathUri!!)
            }else{
                val emptyfirstname=FragedtxtFirstname?.text.toString()
                val emptylastname=FragedtxtLastname?.text.toString()
                val emptyemail=FragedtxtEmail?.text.toString()
                val emptyaddress=edttxtaddress?.text.toString()

                if (TextUtils.isEmpty(emptyfirstname)){
                    Fragedtxtviewdata!!.error = "Please enter firstname"
                }
                else if (TextUtils.isEmpty(emptylastname)){
                    Fragedtxtviewdata!!.error = "Please enter lastname"
                }
                else if (TextUtils.isEmpty(emptyemail)){
                    Fragedtxtviewdata!!.error = "Please enter email"
                }
                else if (TextUtils.isEmpty(emptyaddress)){
                    Fragedtxtviewdata!!.error = "Please select address by click Location icon"
                }
                else if(!checkmale!!.isChecked()&& !checkfemale!!.isChecked){
                    checkfemale?.error="Please select gender"

                }
                validatePassword()
            }

            //store user info to firebase


//            uploadprofileimage()

        }
    }
   private fun saveUserData(imageurl: Uri?) {
       val getfirstname=FragedtxtFirstname?.text.toString()
       val getlastname=FragedtxtLastname?.text.toString()
       val getemail=FragedtxtEmail?.text.toString()
       val getaddress=edttxtaddress?.text.toString()
       val getpassword=password?.text.toString()
       val getmatchpassword=matchpassword?.text.toString()
       val getprofileimage=imageurl.toString()


       val userid= dbref.push().key!!
       val userdta=UserDataModel(userid,getfirstname,getlastname,getemail,maleorfemale,getpassword,getmatchpassword,getaddress,getprofileimage)
       dbref.child(userid).setValue(userdta)
           .addOnCompleteListener {
               Toast.makeText(requireContext(),"success",Toast.LENGTH_LONG).show()
           }


   }

    private fun validatePassword(): Boolean {
        val passwordInput: String = password?.getText().toString().trim()
        // if password field is empty
        // it will display error message "Field can not be empty"
        return if (passwordInput.isEmpty()) {
            password!!.error = "Please enter password"
            false
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password!!.error = "Password is too weak"
            false
        } else {
            password!!.error = null
            true
            matchpasswordvalidation()
        }

    }

    private fun matchpasswordvalidation(): Boolean {

        val password: String = password?.getText().toString().trim()
        val passwordmatchvalidation: String = matchpassword?.getText().toString().trim()
        return if (password==passwordmatchvalidation)
        {
            //matchpassword!!.error="Password Matched"
            true
        }
        else
        {
            matchpassword!!.error="Password don't Matched"
            false
        }


    }

    //select 1 checkbox at atime like male or female
   private fun oncheckedlistner() {

        checkfemale?.setOnCheckedChangeListener { _, isChecked   ->
            if(isChecked){
                checkmale?.isChecked=false
                maleorfemale="Female"

            }


        }

       checkmale?.setOnCheckedChangeListener { compoundButton, b ->
           if (b)
           {
               checkfemale?.isChecked=false
               maleorfemale="Male"
           }
       }
    }


//   //for camera take picture
   private fun onImageclicklistner(){
        profileimageview?.setOnClickListener {


            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add Photo!")
            builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                if (options[item] == "Take Photo") {
                    val intent = Intent(ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 1)
                } else if (options[item] == "Choose from Gallery") {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 2)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            })
            builder.show()
            selectimage.launch("image/*")
//            uploadprofileimage()
        }
   }

    @SuppressLint("SuspiciousIndentation")
    private fun uploadprofileimage(imageUri: Uri) {
        val storageref=FirebaseStorage.getInstance().getReference("User Data")

            storageref.putFile(imageUri).addOnSuccessListener {
                storageref.downloadUrl.addOnSuccessListener{
                    saveUserData(it)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener{
                Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
            }
    }




}

