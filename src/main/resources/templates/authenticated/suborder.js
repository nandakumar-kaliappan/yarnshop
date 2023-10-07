document.addEventListener('DOMContentLoaded', function() {
    const itemList = document.getElementById('item-list');
    const selectedItems = document.getElementById('selected-items');

    itemList.addEventListener('change', function(event) {
        if (event.target.type === 'checkbox') {
            const itemText = event.target.nextSibling.textContent.trim();

            if (event.target.checked) {
                // Add selected item to the right block
                const listItem = document.createElement('li');
                listItem.textContent = itemText;
                selectedItems.appendChild(listItem);
            } else {
                // Remove item from the right block if unchecked
                const items = selectedItems.getElementsByTagName('li');
                for (let i = 0; i < items.length; i++) {
                    if (items[i].textContent.trim() === itemText) {
                        selectedItems.removeChild(items[i]);
                        break;
                    }
                }
            }
        }
    });
});
