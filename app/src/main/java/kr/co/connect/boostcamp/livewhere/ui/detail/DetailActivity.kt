package kr.co.connect.boostcamp.livewhere.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.co.connect.boostcamp.livewhere.R
import kr.co.connect.boostcamp.livewhere.databinding.ActivityDetailBinding
import kr.co.connect.boostcamp.livewhere.model.MarkerInfo
import kr.co.connect.boostcamp.livewhere.ui.map.StreetMapActivity
import kr.co.connect.boostcamp.livewhere.util.generateUuid
import kr.co.connect.boostcamp.livewhere.util.keyboardHide
import kr.co.connect.boostcamp.livewhere.util.keyboardShow
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    companion object {
        private const val DETAIL_CONTAINER_ID = R.id.detail_activity_container
    }

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentFragment: Fragment
    lateinit var markerInfo: MarkerInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        markerInfo = intent.getParcelableExtra("markerInfo")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        if (savedInstanceState == null) {
            addDetailFragment()
        }

        binding.apply {
            viewModel = this@DetailActivity.viewModel
            lifecycleOwner = this@DetailActivity

        }

        viewModel.setUuid(generateUuid(this))
        viewModel.setMarkerInfoFromActivity(markerInfo)

        viewModel.markerInfo.observe(this, Observer {
            //지도 화면으로부터 전체 데이터 넘겨 받았을 때
            setBuildingTitle(binding.detailActivityTvAddress, viewModel.buildingName.get())
            viewModel.getCoordinateFromInfo()
            viewModel.getRecentPriceFromInfo()
            viewModel.getPastTransactionFromList()
        })

        viewModel.transactionMoreClicked.observe(this, Observer {
            //과거 거래내역 더보기 클릭
            addDetailMore()
        })

        viewModel.reviewMoreClicked.observe(this, Observer {
            //거주 후기 더보기 클릭
            addReviewMore()
        })

        viewModel.reviewPostOpenClicked.observe(this, Observer {
            // 작성하기 클릭
            keyboardShow()
            addReviewPost()
        })

        viewModel.reviewPostSuccess.observe(this, Observer {
            //리뷰 작성 완료시
            keyboardHide()
            onBackPressed()
        })

        viewModel.onPressedBackBtn.observe(this, Observer {
            keyboardHide()
            onBackPressed()
        })

        viewModel.getBookmarks().observe(this, Observer {
            viewModel.checkBookmarkId()
        })

        viewModel.openStreetView.observe(this, Observer {
            val intent = Intent(this, StreetMapActivity::class.java)
            intent.putExtra("lat", it.latitude)
            intent.putExtra("lng", it.longitude)
            intent.putExtra("address", it.addressName)
            this.startActivity(intent)
        })
    }

    private fun addDetailFragment() {
        val fragment = DetailFragment.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .replace(DETAIL_CONTAINER_ID, fragment)
            .commit()
    }

    private fun addDetailMore() {
        val fragment = DetailFragmentTransactionMore.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .add(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addReviewMore() {
        val fragment = DetailFragmentReviewMore.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .add(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun addReviewPost() {
        val fragment = DetailFragmentReviewPost.newInstance()
        currentFragment = fragment
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_top,
                R.anim.slide_out_top,
                R.anim.slide_in_bottom,
                R.anim.slide_out_bottom
            )
            .add(DETAIL_CONTAINER_ID, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        viewModel.enterDetailPage()
    }

    override fun onPause() {
        super.onPause()
        viewModel.exitDetailPage()
    }

}