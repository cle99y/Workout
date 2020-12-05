package uk.geeklife.workout

class ExerciseModel(
    private var id: Int,
    private var exerciseName: String,
    private var image: Int,
    private var isComplete: Boolean,
    private var isSelectd: Boolean
) {

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getExerciseName(): String {
        return exerciseName
    }

    fun setExerciseName(exerciseName: String) {
        this.exerciseName = exerciseName
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getIsComplete(): Boolean {
        return isComplete
    }

    fun setIsComplete(isComplete: Boolean) {
        this.isComplete = isComplete
    }

    fun getIsSelected(): Boolean {
        return isSelectd
    }

    fun SetIsSelected(isSelectd: Boolean) {
        this.isSelectd = isSelectd
    }

}