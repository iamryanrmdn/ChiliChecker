package com.capstone.chilichecker.view.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.chilichecker.data.pref.BookmarkModel
import com.capstone.chilichecker.data.repository.BookmarkRepository

class BookmarkViewModel : ViewModel() {

    private val _bookmarks = MutableLiveData<List<BookmarkModel>>()
    val bookmarks: LiveData<List<BookmarkModel>> get() = _bookmarks

    init {
        _bookmarks.value = BookmarkRepository.getBookmarks()
    }

    fun addBookmark(bookmark: BookmarkModel) {
        BookmarkRepository.addBookmark(bookmark)
        _bookmarks.value = BookmarkRepository.getBookmarks()
    }

    fun removeBookmark(bookmark: BookmarkModel) {
        BookmarkRepository.removeBookmark(bookmark)
        _bookmarks.value = BookmarkRepository.getBookmarks()
    }

}