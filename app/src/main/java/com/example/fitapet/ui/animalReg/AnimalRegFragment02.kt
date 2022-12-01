package com.example.fitapet.ui.animalReg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitapet.R
import com.example.fitapet.databinding.FragmentAnimalReg02Binding
import com.example.fitapet.databinding.FragmentAnimalRegBinding


class AnimalRegFragment02 : Fragment(),View.OnClickListener {
    private var _binding: FragmentAnimalReg02Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimalReg02Binding.inflate(inflater, container, false)
        val root: View = binding.root


        //부위
        binding.animalReg2PartBtn01.setOnClickListener(this)
        binding.animalReg2PartBtn02.setOnClickListener(this)
        binding.animalReg2PartBtn03.setOnClickListener(this)
        binding.animalReg2PartBtn04.setOnClickListener(this)
        binding.animalReg2PartBtn05.setOnClickListener(this)
        binding.animalReg2PartBtn06.setOnClickListener(this)
        binding.animalReg2PartBtn07.setOnClickListener(this)
        binding.animalReg2PartBtn08.setOnClickListener(this)
        //펫만남 반응
        binding.animalReg2PeoReactBtn01.setOnClickListener(this)
        binding.animalReg2PeoReactBtn02.setOnClickListener(this)
        binding.animalReg2PeoReactBtn03.setOnClickListener(this)
        binding.animalReg2PeoReactBtn04.setOnClickListener(this)
        //펫만남 반응
        binding.animalReg2PetReactBtn01.setOnClickListener(this)
        binding.animalReg2PetReactBtn02.setOnClickListener(this)
        binding.animalReg2PetReactBtn03.setOnClickListener(this)
        binding.animalReg2PetReactBtn04.setOnClickListener(this)
        //공격 유무
        binding.animalReg2AttackBtn01.setOnClickListener(this)
        binding.animalReg2AttackBtn02.setOnClickListener(this)
        //예방접종 뮤무
        binding.animalReg2VaccinBtn01.setOnClickListener(this)
        binding.animalReg2VaccinBtn02.setOnClickListener(this)

        val view = binding.root

        return root
    }




    override fun onClick(v: View?) {
        when(v?.id){
            binding.animalReg2PartBtn01.id->{
                binding.animalReg2PartBtn01.isSelected=binding.animalReg2PartBtn01.isSelected!=true
            }
            binding.animalReg2PartBtn02.id->{
                binding.animalReg2PartBtn02.isSelected=binding.animalReg2PartBtn02.isSelected!=true
            }
            binding.animalReg2PartBtn03.id->{
                binding.animalReg2PartBtn03.isSelected=binding.animalReg2PartBtn03.isSelected!=true
            }
            binding.animalReg2PartBtn04.id->{
                binding.animalReg2PartBtn04.isSelected=binding.animalReg2PartBtn04.isSelected!=true
            }
            binding.animalReg2PartBtn05.id->{
                binding.animalReg2PartBtn05.isSelected=binding.animalReg2PartBtn05.isSelected!=true
            }
            binding.animalReg2PartBtn06.id->{
                binding.animalReg2PartBtn06.isSelected=binding.animalReg2PartBtn06.isSelected!=true
            }
            binding.animalReg2PartBtn07.id->{
                binding.animalReg2PartBtn07.isSelected=binding.animalReg2PartBtn07.isSelected!=true
            }
            binding.animalReg2PartBtn08.id->{
                binding.animalReg2PartBtn08.isSelected=binding.animalReg2PartBtn08.isSelected!=true
            }
            binding.animalReg2PeoReactBtn01.id->{
                binding.animalReg2PeoReactBtn01.isSelected=binding.animalReg2PeoReactBtn01.isSelected!=true
            }
            binding.animalReg2PeoReactBtn02.id->{
                binding.animalReg2PeoReactBtn02.isSelected=binding.animalReg2PeoReactBtn02.isSelected!=true
            }
            binding.animalReg2PeoReactBtn03.id->{
                binding.animalReg2PeoReactBtn03.isSelected=binding.animalReg2PeoReactBtn03.isSelected!=true
            }
            binding.animalReg2PeoReactBtn04.id->{
                binding.animalReg2PeoReactBtn04.isSelected=binding.animalReg2PeoReactBtn04.isSelected!=true
            }
            binding.animalReg2PetReactBtn01.id->{
                binding.animalReg2PetReactBtn01.isSelected=binding.animalReg2PetReactBtn01.isSelected!=true
            }
            binding.animalReg2PetReactBtn02.id->{
                binding.animalReg2PetReactBtn02.isSelected=binding.animalReg2PetReactBtn02.isSelected!=true
            }
            binding.animalReg2PetReactBtn03.id->{
                binding.animalReg2PetReactBtn03.isSelected=binding.animalReg2PetReactBtn03.isSelected!=true
            }
            binding.animalReg2PetReactBtn04.id->{
                binding.animalReg2PetReactBtn04.isSelected=binding.animalReg2PetReactBtn04.isSelected!=true
            }
            binding.animalReg2AttackBtn01.id->{
                binding.animalReg2AttackBtn01.isSelected=binding.animalReg2AttackBtn01.isSelected!=true
                binding.animalReg2AttackBtn02.isSelected=false
            }
            binding.animalReg2AttackBtn02.id->{
                binding.animalReg2AttackBtn02.isSelected=binding.animalReg2AttackBtn02.isSelected!=true
                binding.animalReg2AttackBtn01.isSelected=false
            }
            binding.animalReg2VaccinBtn01.id->{
                binding.animalReg2VaccinBtn01.isSelected=binding.animalReg2VaccinBtn01.isSelected!=true
                binding.animalReg2VaccinBtn02.isSelected=false
            }
            binding.animalReg2VaccinBtn02.id->{
                binding.animalReg2VaccinBtn02.isSelected=binding.animalReg2VaccinBtn02.isSelected!=true
                binding.animalReg2VaccinBtn01.isSelected=false
            }
        }
    }

}