package sun.trainingcourse.anticovid_19.data.resource

class CovidRepository private constructor(
    private val remote: CovidDataSource.Remote
): CovidDataSource.Local, CovidDataSource.Remote {

    override fun getCovidInformation() {
    }

    companion object {
        private var instance: CovidRepository? = null
        fun getInstance(remote: CovidDataSource.Remote) =
            instance ?: CovidRepository(remote).also { instance = it }
    }
}
