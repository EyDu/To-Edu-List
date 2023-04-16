import {Outlet} from "react-router-dom";
import Footer from "../footer.jsx";
import Header from "../header.jsx";

export default function Root() {

    return (
        <>
            <Header />
            <Outlet />
            <Footer />
        </>
    )
}