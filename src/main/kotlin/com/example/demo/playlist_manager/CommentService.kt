package com.example.demo.playlist_manager

import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
    fun getCommentsByPlaylist(playlist: Playlist) : List<Comm> {
        return commentRepository.findByPlaylist(playlist)
    }

    fun getCommentsByUser(user: User) : List<Comm> {
        return commentRepository.findByUser(user)
    }

    fun addComment(comment: Comm, user: User, playlist: Playlist) : Comm? {
        try {
            comment.user = user
            comment.playlist = playlist
            return commentRepository.save(comment)
        }
        catch(e : Exception){
            e.printStackTrace()
            return null
        }

    }

    fun deleteCommentbyId(commentId: Long) : Boolean {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId)
            return true
        }
        return false
    }

    fun deleteCommentsByPlaylist(playlist: Playlist) : Boolean {
        val comments = getCommentsByPlaylist(playlist)
        for (comment in comments) {
            deleteCommentbyId(comment.id!!)
        }
        return comments.isNotEmpty()
    }

    fun deleteCommentsByUser(user : User) : Boolean {
        val comments = getCommentsByUser(user)
        for (comment in comments) {
            deleteCommentbyId(comment.id!!)
        }
        return comments.isNotEmpty()
    }

}