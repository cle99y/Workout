package uk.geeklife.config

import uk.geeklife.workout.ExerciseModel
import uk.geeklife.workout.R

class Utility {

    companion object {

        fun defaultExerciseList(): ArrayList<ExerciseModel> {

            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(
                1,
                "Jumping Jacks",
                IMG_JUMPING_JACK,
                false,
                false
            )
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(
                2,
                "Wall Sit",
                IMG_WALL_SIT,
                false,
                false
            )
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(
                3,
                "Push Up",
                IMG_PUSH_UP,
                false,
                false
            )
            exerciseList.add(pushUp)

            val abdominalCrunch =
                ExerciseModel(
                    4,
                    "Abdominal Crunch",
                    IMG_ABDOMINAL_CRUNCH,
                    false,
                    false
                )
            exerciseList.add(abdominalCrunch)

            val stepUpOnChair = ExerciseModel(
                5,
                "Step-Up onto Chair",
                IMG_STEP_ON_CHAIR,
                false,
                false
            )
            exerciseList.add(stepUpOnChair)

            val squat = ExerciseModel(
                6,
                "Squat",
                IMG_SQUAT,
                false,
                false
            )
            exerciseList.add(squat)

            val tricepDipOnChair = ExerciseModel(
                7,
                "Tricep Dip On Chair",
                IMG_TRICEP_DIP_ON_CHAIR,
                false,
                false
            )
            exerciseList.add(tricepDipOnChair)

            val plank = ExerciseModel(
                8,
                "Plank",
                IMG_PLANK,
                false,
                false
            )
            exerciseList.add(plank)

            val highKneesRunningInPlace = ExerciseModel(
                9,
                "High Knees Running In Place",
                IMG_HIGH_KNEES_RUNNING,
                false,
                false
            )
            exerciseList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(
                10,
                "Lunges",
                IMG_LUNGE,
                false,
                false
            )
            exerciseList.add(lunges)

            val pushupAndRotation =
                ExerciseModel(
                    11,
                    "Push up and Rotation",
                    IMG_PUSH_UP_ROTATION,
                    false,
                    false
                )
            exerciseList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(
                12,
                "Side Plank",
                IMG_SIDE_PLANK,
                false,
                false
            )
            exerciseList.add(sidePlank)

            return exerciseList
        }

        private const val IMG_JUMPING_JACK = R.drawable.ic_jumping_jacks
        private const val IMG_PUSH_UP_ROTATION = R.drawable.ic_push_up_and_rotation
        private const val IMG_HIGH_KNEES_RUNNING = R.drawable.ic_high_knees_running_in_place
        private const val IMG_TRICEP_DIP_ON_CHAIR = R.drawable.ic_triceps_dip_on_chair
        private const val IMG_SQUAT = R.drawable.ic_squat
        private const val IMG_STEP_ON_CHAIR = R.drawable.ic_step_up_onto_chair
        private const val IMG_ABDOMINAL_CRUNCH = R.drawable.ic_abdominal_crunch
        private const val IMG_WALL_SIT = R.drawable.ic_wall_sit
        private const val IMG_PUSH_UP = R.drawable.ic_push_up
        private const val IMG_PLANK = R.drawable.ic_plank
        private const val IMG_SIDE_PLANK = R.drawable.ic_side_plank
        private const val IMG_LUNGE = R.drawable.ic_lunge


    }
}