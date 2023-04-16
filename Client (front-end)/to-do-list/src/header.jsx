import {Link} from "react-router-dom";

export default function Header() {


    return (
        <>
            <header>
                <div>This is the header</div>
                <Link to={"/notes"}> My Notes </Link>
                <Link to={"/notes/add"}>Add Notes</Link>
            </header>
        </>
    )
}