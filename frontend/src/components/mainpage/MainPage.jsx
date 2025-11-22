import styles from "./MainPage.module.css";
function MainPage() {

    async function displayMessage() {
        console.log("Say hi to the browsers console!")
    }

    displayMessage();

    return (
        <>
            <h1>Lets go from Main</h1>
        </>
    )
}

export default MainPage;
