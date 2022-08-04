package hoang.nguyenminh.smartexam.model

interface IQueryMapParam {
    fun build() = hashMapOf<String, String>().also {
        this.putQueries(it)
    }

    fun putQueries(map: MutableMap<String, String>)
}