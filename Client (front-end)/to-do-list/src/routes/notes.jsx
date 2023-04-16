import React, {useState, useEffect } from 'react';
import {Status} from "../types.ts";
import {Link} from "react-router-dom";

export default function Notes() {
    const [notes, setNotes] = useState([]);
    const [formData, setFormData] = useState({
        id: '',
        message: '',
        status: Status.NOT_DONE
    });

    useEffect(() => {
        fetch("http://localhost:8080/api/notes")
            .then(response => response.json())
            .then(notes => setNotes(notes))
    }, [notes])

    const handleInputChange = event => {
        const { name, value } = event.target;
        setFormData( {...formData, [name]: value })
    }

    const handleAddNote = event => {
        event.preventDefault();

        const note = {
            id: Math.floor(Math.random() * 100),
            message: formData.message,
            status: Status[formData.status]
        }

        fetch("http://localhost:8080/api/notes", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(note)
        })
            .then(response => {
                return response.json();
            })
            .then(newNote => {
                setNotes([...notes, newNote]);
                setFormData({ id: '', message: '', status: '' });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    const handleUpdateNote = event => {
        event.preventDefault();

        const note = {
            id: formData.id,
            message: formData.message,
            status: Status[formData.status]
        }

        fetch("http://localhost:8080/api/notes", {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(note)
        })
            .then(response => {
                return response.json();
            })
            .then(newNote => {
                setNotes([...notes, newNote]);
                setFormData({ id: '', message: '', status: '' });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }


    const handleDeleteNote = (noteId) => {
        fetch(`http://localhost:8080/api/notes/${noteId}`, {
            method: 'DELETE'
        })
            .then(() => setNotes(notes.filter(note => note.id !== noteId)))
    }



    return (
        <>
            <div>
                <h1>My Notes</h1>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Message</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {notes.map(note => (
                        <tr key={note.id}>
                            <td>{note.id}</td>
                            <td>{note.message}</td>
                            <td>{note.status}</td>
                            <td>
                                <button onClick={() => handleDeleteNote(note.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <h3>Update Note</h3>
                <form onSubmit={handleUpdateNote}>
                    <label>
                        Id:
                        <input type={"number"} name={"id"} value={formData.id} onChange={handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Message:
                        <input type={"text"} name={"message"} value={formData.message} onChange={handleInputChange} />
                    </label>
                    <br />
                    <label>
                        Status:
                        <select name="status" value={formData.status} onChange={handleInputChange}>
                            <option value="DONE">Done</option>
                            <option value="NOT_DONE">Not done</option>
                            <option value="IN_WORK">In Work</option>
                            <option value="FORGOTTEN">Forgotten</option>
                        </select>
                    </label>
                    <br />
                    <button type={"submit"}>Update Note</button>
                </form>
            </div>
        </>
    )
}