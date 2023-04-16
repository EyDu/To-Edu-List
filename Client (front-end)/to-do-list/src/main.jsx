import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {RouterProvider, createBrowserRouter} from "react-router-dom";
import Root from "./routes/root.jsx";
import ErrorPage from "./error.jsx";
import Index from "./routes/index.jsx";
import Notes from "./routes/notes.jsx";
import AddNote from "./routes/addNote.jsx";



const router = createBrowserRouter ([
    {
        path: "/",
        element: <Root />,
        errorElement: <ErrorPage />,
        children: [
            {
                index: true,
                element: <Index />
            },
            {
                path: "/notes",
                element: <Notes />,
            },
            {
                path: "/notes/add",
                element: <AddNote />,
            }
        ]
    }
    ]
)

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
