package com.capstone.chilichecker.data.repository

import com.capstone.chilichecker.data.pref.BookmarkModel

object BookmarkRepository {
    private val bookmarks = mutableListOf<BookmarkModel>()

    fun addBookmark(bookmark: BookmarkModel) {
        bookmarks.add(bookmark)
    }

    fun getBookmarks(): List<BookmarkModel> {
        return bookmarks
    }

    fun removeBookmark(bookmark: BookmarkModel) {
        bookmarks.remove(bookmark)
    }
}