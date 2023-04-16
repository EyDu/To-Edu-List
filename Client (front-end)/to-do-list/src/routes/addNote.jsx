import {useState} from "react";
import {Status} from "../types.ts";

export default function AddNote() {
    const [notes, setNotes] = useState([]);
    const [formData, setFormData] = useState({
        id: '',
        message: '',
        status: Status.NOT_DONE
    });

    const handleAddNote = event => {
        event.preventDefault();

        const note = {
            id: Math.floor(Math.random() * 100),
            message: formData.message,
            status: formData.status
        }

        fetch("http://localhost:8080/api/notes", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(note)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json()
            })
        .then(newNote => {
            setNotes([...notes, newNote])
            setFormData({message: '', status: ''})
        })
            .catch(error => {
              console.error(error);
            })
    };

    const handleInputChange = event => {
        const { name, value } = event.target;
        setFormData( {...formData, [name]: value })
    }

    return (

        <>
            <h1> Add Note </h1>
                <form onSubmit={handleAddNote}>
                    <label>Message:
                        <input type={"text"} name="message" value={formData.message} onChange={handleInputChange}></input>
                    </label>
                    <br />
                    <select name="status" value={formData.status} onChange={handleInputChange} defaultValue={Status.NOT_DONE}>
                        <option value={Status.DONE}>Done</option>
                        <option value={Status.NOT_DONE}>Not done</option>
                        <option value={Status.IN_WORK}>In Work</option>
                        <option value={Status.FORGOTTEN}>Forgotten</option>
                    </select>
                    <br />
                    <button type={"submit"}>Add Note</button>
                </form>
        </>
    )
}