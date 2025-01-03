console.log("contact");
const viewContactModal = document.getElementById('view_contact_modal');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactdata(id) {
  try{ const data=await(
      await fetch(`http://localhost:8080/api/contact/${id}`)
  ).json();
 document.querySelector("#contact_name").innerHTML = data.name;
      document.querySelector("#contact_email").innerHTML = data.email;
 openContactModal();
      console.log(data);
  }catch (error){console.log("Error:" ,error);}


}